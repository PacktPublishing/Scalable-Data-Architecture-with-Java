package com.scalabledataarchitecture.etl.config;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchJobConfiguration {

    @Bean
    public Job etlJob(JobBuilderFactory jobs,
                      Step fileCheck, Step fileMoveToProcess, Step processFile,Step fileMoveToArchive, Step fileMoveToError) {
        return jobs.get("etlJob")
                .start(fileCheck).on(ExitStatus.STOPPED.getExitCode()).end()
                .next(fileMoveToProcess)
                .next(processFile).on(ExitStatus.COMPLETED.getExitCode()).to(fileMoveToArchive)
                .from(processFile).on(ExitStatus.FAILED.getExitCode()).to(fileMoveToError)
                .end()
                .build();
    }
}
