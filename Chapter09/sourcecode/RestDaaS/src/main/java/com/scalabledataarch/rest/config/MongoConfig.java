package com.scalabledataarch.rest.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.scalabledataarch.rest.repository")
public class MongoConfig {

    @Value(value = "${restdaas.mongoUrl}")
    private String mongoUrl;

    @Value(value = "${restdaas.mongoDb}")
    private String mongoDb;

    @Bean
    public MongoClient mongo() throws Exception {
        final ConnectionString connectionString = new ConnectionString(mongoUrl);
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).serverApi(ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build()).build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), mongoDb);
    }
}
