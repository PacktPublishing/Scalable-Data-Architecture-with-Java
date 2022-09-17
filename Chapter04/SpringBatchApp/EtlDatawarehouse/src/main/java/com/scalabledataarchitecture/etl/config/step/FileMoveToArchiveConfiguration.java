package com.scalabledataarchitecture.etl.config.step;

import com.scalabledataarchitecture.etl.config.EnvFolderProperty;
import com.scalabledataarchitecture.etl.steps.FileMoveToArchiveTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileMoveToArchiveConfiguration {

    @Bean
    public Tasklet fileMoveToArchiveTasklet(EnvFolderProperty envFolderProperty) {
        return new FileMoveToArchiveTasklet(envFolderProperty);
    }

    @Bean
    public Step fileMoveToArchive(StepBuilderFactory stepBuilderFactory, Tasklet fileMoveToArchiveTasklet) {
        return stepBuilderFactory.get("fileMoveToArchive")
                .tasklet(fileMoveToArchiveTasklet)
                .build();
    }
}
