package com.scalabaledataarch.realtime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
@Configuration
public class CreditRiskCalculatorApp {

    public static void main(String[] args) {
        SpringApplication.run(CreditRiskCalculatorApp.class);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
