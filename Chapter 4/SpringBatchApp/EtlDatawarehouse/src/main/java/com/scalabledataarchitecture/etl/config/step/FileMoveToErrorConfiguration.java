package com.scalabledataarchitecture.etl.config.step;

import com.scalabledataarchitecture.etl.config.EnvFolderProperty;
import com.scalabledataarchitecture.etl.steps.FileMoveToErrorTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileMoveToErrorConfiguration {

    @Bean
    public Tasklet fileMoveToErrorTasklet(EnvFolderProperty envFolderProperty) {
        return new FileMoveToErrorTasklet(envFolderProperty);
    }

    @Bean
    public Step fileMoveToError(StepBuilderFactory stepBuilderFactory, Tasklet fileMoveToErrorTasklet) {
        return stepBuilderFactory.get("fileMoveToError")
                .tasklet(fileMoveToErrorTasklet)
                .build();
    }
}
