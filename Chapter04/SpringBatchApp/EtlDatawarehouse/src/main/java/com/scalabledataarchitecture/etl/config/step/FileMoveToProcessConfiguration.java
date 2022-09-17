package com.scalabledataarchitecture.etl.config.step;

import com.scalabledataarchitecture.etl.config.EnvFolderProperty;
import com.scalabledataarchitecture.etl.steps.FileMoveToProcessTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileMoveToProcessConfiguration {

    @Bean
    public Tasklet fileMoveToProcessTasklet(EnvFolderProperty envFolderProperty) {
        return new FileMoveToProcessTasklet(envFolderProperty);
    }

    @Bean
    public Step fileMoveToProcess(StepBuilderFactory stepBuilderFactory, Tasklet fileMoveToProcessTasklet) {
        return stepBuilderFactory.get("fileMoveToProcess")
                .tasklet(fileMoveToProcessTasklet)
                .build();
    }
}
