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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileMoveToArchiveTasklet implements Tasklet, StepExecutionListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(FileMoveToArchiveTasklet.class);
    private final EnvFolderProperty envFolderProperty;
    private Path filepath;

    public FileMoveToArchiveTasklet(EnvFolderProperty envFolderProperty) {
        this.envFolderProperty = envFolderProperty;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.filepath = (Path) stepExecution.getJobExecution().getExecutionContext().get("filepath");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        assert Files.exists(filepath);
        Path dest = Paths.get(envFolderProperty.getArchive() + File.separator + filepath.getFileName());
        LOGGER.info("Moving {} to {}", filepath, dest);
        Files.move(filepath, dest, StandardCopyOption.REPLACE_EXISTING);
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }


}
