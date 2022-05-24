package com.scalabledataarch.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.scalabledataarch.rest")
public class RestDaaSApp {

    public static void main(String[] args) {
        SpringApplication.run(RestDaaSApp.class);
    }
}
