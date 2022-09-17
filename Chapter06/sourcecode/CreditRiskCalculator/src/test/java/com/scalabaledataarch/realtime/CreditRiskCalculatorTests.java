package com.scalabaledataarch.realtime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalabaledataarch.realtime.config.StreamBuilder;
import com.scalabaledataarch.realtime.model.CreditRecord;
import com.scalabaledataarch.realtime.model.RiskScoreResponse;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class CreditRiskCalculatorTests {

    private final Properties config;

    public CreditRiskCalculatorTests() {
        config = new Properties();
        config.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "testApp");
        config.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "test:1234");
        config.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        config.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

    }

    @Mock
    JdbcTemplate jdbcTemplate;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void creditRiskStreamsTest() throws JsonProcessingException {
        //test input and outputTopic
        String inputTopicName = "testInputTopic";
        String outputTopicName = "testOutputTopic";
        ObjectMapper mapper = new ObjectMapper();
        //ReflectionTestUtils.setField(employeeDAO, "jdbcTemplate", jdbcTemplate);

        //mock jdbc call
        List<CreditRecord> creditRecords = new ArrayList<>();
        CreditRecord creditRecord = new CreditRecord();
        creditRecord.setMonths_balance(3L);
        creditRecord.setStatus("C");
        creditRecords.add(creditRecord);
        Mockito.lenient().when(jdbcTemplate.query(Mockito.anyString(),Mockito.eq(new BeanPropertyRowMapper<CreditRecord>(CreditRecord.class))))
                .thenReturn(creditRecords);

        //mock REST response
        RiskScoreResponse riskScoreResponse = new RiskScoreResponse();
        riskScoreResponse.setScore(3);
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.POST), Mockito.any(), Mockito.eq(RiskScoreResponse.class)))
          .thenReturn(new ResponseEntity(riskScoreResponse, HttpStatus.OK));

        StreamsBuilder builder = new StreamsBuilder();
        StreamBuilder.INSTANCE.getkStream(builder,inputTopicName,outputTopicName,"any url",jdbcTemplate,restTemplate);
        Topology testTopology = builder.build();
        TopologyTestDriver testDriver = new TopologyTestDriver(testTopology,config);
        TestInputTopic<String,String> inputTopic = testDriver.createInputTopic(inputTopicName, Serdes.String().serializer(), Serdes.String().serializer());
        TestOutputTopic<String,String> outputTopic = testDriver.createOutputTopic(outputTopicName, Serdes.String().deserializer(), Serdes.String().deserializer());

        assertThat(outputTopic.isEmpty(),is(true));

        String inputPayload = "{ \"id\": \"5008804\", \"genderCode\": \"M\", \"flagOwnCar\": \"Y\", \"flagOwnRealty\": \"Y\", \"cntChildren\": 0, \"amtIncomeTotal\": 427500, \"nameIncomeType\": \"Working\", \"nameEducationType\": \"Higher education\", \"nameFamilyStatus\": \"Civil marriage\", \"nameHousingType\": \"Rented apartment\", \"daysBirth\": -12005, \"daysEmployed\": -4542, \"flagMobil\": 1, \"flagWorkPhone\": 1, \"flagPhone\": 0, \"flagEmail\": 0, \"occupationType\": \"\", \"cntFamMembers\": 2 }";
        inputTopic.pipeInput(inputPayload);
        assertEquals(mapper.readTree(outputTopic.readValue()), mapper.readTree("{ \"id\": \"5008804\", \"genderCode\": \"M\", \"flagOwnCar\": \"Y\", \"flagOwnRealty\": \"Y\", \"cntChildren\": 0, \"amtIncomeTotal\": 427500.0, \"nameIncomeType\": \"Working\", \"nameEducationType\": \"Higher education\", \"nameFamilyStatus\": \"Civil marriage\", \"nameHousingType\": \"Rented apartment\", \"daysBirth\": -12005, \"daysEmployed\": -4542, \"flagMobil\": 1, \"flagWorkPhone\": 1, \"flagPhone\": 0, \"flagEmail\": 0, \"occupationType\": \"\", \"cntFamMembers\": 2 , \"riskScore\": 3.0}"));

    }
}
