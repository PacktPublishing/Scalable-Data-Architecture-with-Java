package com.scalabaledataarch.realtime.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalabaledataarch.realtime.config.KStreamConfiguration;
import com.scalabaledataarch.realtime.model.*;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;
import java.util.List;

public class RiskCalculateTransformer implements Transformer<String,String, KeyValue<String,String>> {

    private static Logger logger = LoggerFactory.getLogger(RiskCalculateTransformer.class);

    private ObjectMapper mapper;
    private JdbcTemplate jdbcTemplate;
    private RestTemplate restTemplate;
    private String mlRequestUrl;

    public RiskCalculateTransformer(JdbcTemplate jdbcTemplate,RestTemplate restTemplate,String mlRequestUrl) {
        mapper = new ObjectMapper();
        this.jdbcTemplate = jdbcTemplate;
        this.restTemplate = restTemplate;
        this.mlRequestUrl = mlRequestUrl;
    }

    @Override
    public void init(ProcessorContext processorContext) {

    }

    @Override
    public KeyValue<String, String> transform(String key, String value) {
        try {
            logger.debug("value: "+value);
            ApplicationEvent event = mapper.readValue(value,ApplicationEvent.class);
            logger.debug("Application event deserialized");
            List<CreditRecord> creditRecord = jdbcTemplate.query(String.format("select months_balance,status from chapter6.creditrecord where id='%s'",event.getId()),new BeanPropertyRowMapper<CreditRecord>(CreditRecord.class));
            logger.debug("credit records fetched");

            MLRequest mlRequest = new MLRequest();
            mlRequest.setAmtIncomeTotal(event.getAmtIncomeTotal());
            mlRequest.setCntChildren(event.getCntChildren());
            mlRequest.setEmploymentStatus(event.getDaysEmployed()>0);
            mlRequest.setMonthsEmployed(getMonthsEmployed(event.getDaysEmployed()));
            mlRequest.setFlagOwnCar(event.getFlagOwnCar());
            mlRequest.setFlagOwnRealty(event.getFlagOwnRealty());
            mlRequest.setGenderCode(event.getGenderCode());
            mlRequest.setId(event.getId());
            mlRequest.setNameFamilyStatus(event.getNameFamilyStatus());
            mlRequest.setNameHousingType(event.getNameHousingType());
            logger.debug("mlRequest populated");
            HttpEntity<MLRequest> request = new HttpEntity<>(mlRequest);
            logger.debug("mlRequest wrapped in HTTPEntity ");
            ResponseEntity<RiskScoreResponse> response = restTemplate.exchange(mlRequestUrl, HttpMethod.POST, request, RiskScoreResponse.class);
            logger.debug("REST response from RiskScore generator api  recieved ");
            if(response.getStatusCode()== HttpStatus.OK){
                logger.debug("REST response status is OK");
                EnrichedApplication enrichedApplicationEvent = new EnrichedApplication();
                enrichedApplicationEvent.setApplicationforEnrichedApplication(event);
                enrichedApplicationEvent.setRiskScore(response.getBody().getScore());
                return KeyValue.pair(key,mapper.writeValueAsString(enrichedApplicationEvent));
            }else{
                logger.debug("REST response status is NOT OK");
                throw new Exception("Unable to generate risk score.Risk REST response - "+ response.getStatusCode());
            }

        } catch (Exception e) {
            logger.error(String.format("Application event: '%s' is dropped from further processing due to the error",value),e);
            jdbcTemplate.update(
                    "INSERT INTO chapter6.error (errormsg, event) VALUES (?, ?)",
                    e.getMessage(), value
            );
        }
        return null;
    }

    private long getMonthsEmployed(long daysEmployed) {
        if(daysEmployed>0){
            return 0;
        }
        return Math.round(-1*daysEmployed*0.032855);
    }

    @Override
    public void close() {

    }
}
