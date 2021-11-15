package com.scalabledataarchitecture.etl.config.step;

import com.scalabledataarchitecture.etl.config.EnvFolderProperty;
import com.scalabledataarchitecture.etl.steps.FileCheckingTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileCheckConfiguration {

    @Bean
    public Tasklet fileCheckingTasklet(EnvFolderProperty envFolderProperty) {
        return new FileCheckingTasklet(envFolderProperty);
    }

    @Bean
    public Step fileCheck(StepBuilderFactory stepBuilderFactory, Tasklet fileCheckingTasklet) {
        return stepBuilderFactory.get("fileCheck")
                .tasklet(fileCheckingTasklet)
                .build();
    }
}
