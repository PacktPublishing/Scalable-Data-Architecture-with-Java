package com.scalabaledataarch.realtime.config;

import com.scalabaledataarch.realtime.service.RiskCalculateTransformer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

public enum StreamBuilder {
    INSTANCE;
    public KStream<String, String> getkStream(StreamsBuilder builder, String inputTopic,String outTopic, String mlRequestUrl, JdbcTemplate jdbcTemplate, RestTemplate restTemplate) {
        KStream<String,String> kStream = builder.stream(inputTopic);
        kStream.transform(()->new RiskCalculateTransformer(jdbcTemplate,restTemplate,mlRequestUrl)).to(outTopic);
        return kStream;
    }
}
