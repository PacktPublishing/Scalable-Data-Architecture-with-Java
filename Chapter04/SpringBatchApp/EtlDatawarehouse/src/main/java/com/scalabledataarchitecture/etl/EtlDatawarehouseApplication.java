package com.scalabledataarchitecture.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableConfigurationProperties
@EnableScheduling
@ComponentScan({"com.scalabledataarchitecture.etl.*", "com.scalabledataarchitecture.etl.config.*"})
@SpringBootApplication
public class EtlDatawarehouseApplication {

    private static Logger LOGGER = LoggerFactory.getLogger(EtlDatawarehouseApplication.class);

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job etlJob;

    public static void main(String[] args) {
        try {
            SpringApplication.run(EtlDatawarehouseApplication.class, args);
        } catch (Throwable ex) {
            LOGGER.error("Failed to start Spring Boot application: ", ex);
        }
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void perform() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(etlJob, params);
    }

}
