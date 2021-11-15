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
import java.util.List;
import java.util.stream.Collectors;

public class FileMoveToProcessTasklet implements Tasklet, StepExecutionListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(FileMoveToProcessTasklet.class);
    private final EnvFolderProperty envFolderProperty;
    private Path filepath;

    public FileMoveToProcessTasklet(EnvFolderProperty envFolderProperty) {
        this.envFolderProperty = envFolderProperty;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Path dir = Paths.get(envFolderProperty.getRead());
        assert Files.isDirectory(dir);
        List<Path> files = Files.list(dir).filter(p -> !Files.isDirectory(p)).collect(Collectors.toList());
        if (!files.isEmpty()) {
            Path file = files.get(0);
            Path dest = Paths.get(envFolderProperty.getProcess() + File.separator + file.getFileName());
            LOGGER.info("Moving {} to {}", file, dest);
            Files.move(file, dest);
            filepath = dest;
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (filepath != null) {
            stepExecution.getJobExecution().getExecutionContext().put("filepath", filepath);
            stepExecution.getJobExecution().getExecutionContext().put("filepathName", filepath.toString());
        }
        return ExitStatus.COMPLETED;
    }

}
