package com.scalabledataarchitecture.etl.steps;

import com.scalabledataarchitecture.etl.config.EnvFolderProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileCheckingTasklet implements Tasklet, StepExecutionListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(FileCheckingTasklet.class);

    private final EnvFolderProperty envFolderProperty;

    public FileCheckingTasklet(EnvFolderProperty envFolderProperty) {
        this.envFolderProperty = envFolderProperty;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        Path dir = Paths.get(envFolderProperty.getRead());
        LOGGER.debug("Checking if read directory {} contains some files...", dir);
        try {
            List<Path> files = Files.list(dir).filter(p -> !Files.isDirectory(p)).collect(Collectors.toList());
            if (files.isEmpty()) {
                LOGGER.info("Read directory {} does not contain any file. The job is stopped.", dir);
                return ExitStatus.STOPPED;
            }
            LOGGER.info("Read directory {} is not empty. We continue the job.", dir);
            return ExitStatus.COMPLETED;
        } catch (IOException e) {
            LOGGER.error("An error occured while checking if read directory contains files.", e);
            return ExitStatus.FAILED;
        }

    }
}
