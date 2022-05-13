package com.scalabaledataarch.realtime.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalabaledataarch.realtime.service.RiskCalculateTransformer;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KStreamConfiguration {

    private static Logger logger = LoggerFactory.getLogger(KStreamConfiguration.class);

    @Value(value = "${riskcalc.bootstrap-servers}")
    private String bootstrapServer;

    @Value(value = "${riskcalc.appId}")
    private String appId;

    @Value(value = "${riskcalc.inputTopic}")
    private String inputTopic;

    @Value(value = "${riskcalc.outTopic}")
    private String outTopic;

    @Value(value = "${riskcalc.mlRequestUrl}")
    private String mlRequestUrl;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RestTemplate restTemplate;



    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfig(){
        Map<String,Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,appId);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class);

        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public KStream<String,String> kStream(StreamsBuilder builder){
        KStream<String, String> kStream = StreamBuilder.INSTANCE.getkStream(builder,inputTopic,outTopic,mlRequestUrl,jdbcTemplate,restTemplate);
        return kStream;
    }


}
