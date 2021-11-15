package com.scalabledataarchitecture.etl.config.step;

import com.scalabledataarchitecture.etl.model.DeviceEventLogFact;
import com.scalabledataarchitecture.etl.model.EventLogODL;
import com.scalabledataarchitecture.etl.steps.DeviceEventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

@Configuration
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ProcessFileConfiguration {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProcessFileConfiguration.class);

    @Value("${process.chunk_size:1}")
    int chunkSize;

    @Autowired
    DataSource dataSource;

    @Autowired
    DeviceEventProcessor deviceEventProcessor;

    @Bean
    @StepScope
    public FlatFileItemReader<EventLogODL> csvRecordReader(@Value("#{jobExecutionContext['filepathName']}") String filePathName)
            throws UnexpectedInputException, ParseException {
        FlatFileItemReader<EventLogODL> reader = new FlatFileItemReader<EventLogODL>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = {"incidentNumber", "deviceSerialNum", "eventCode", "loggedTime", "closureTime", "status", "assignedTo", "resolutionComment"};
        tokenizer.setNames(tokens);
        reader.setResource(new FileSystemResource(filePathName));
        reader.setLinesToSkip(1);
        DefaultLineMapper<EventLogODL> lineMapper =
                new DefaultLineMapper<EventLogODL>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new BeanWrapperFieldSetMapper<EventLogODL>() {
            {
                setTargetType(EventLogODL.class);
            }
        });
        reader.setLineMapper(lineMapper);
        return reader;
    }


    @Bean
    public JdbcBatchItemWriter<DeviceEventLogFact> jdbcWriter() {
        JdbcBatchItemWriter<DeviceEventLogFact> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO chapter4.device_event_log_fact(eventlogid,deviceid,eventid,hourid,monthid,quarterid,eventtimestamp,closurestatus,closureduration) VALUES (:eventLogId, :deviceId, :eventId, :hourId, :monthId, :quarterId, :eventTimestamp, :closureStatus, :closureDuration)");
        writer.setDataSource(this.dataSource);
        return writer;
    }


    @Bean
    public Step processFile(StepBuilderFactory stepBuilderFactory, ItemReader<EventLogODL> csvRecordReader, JdbcBatchItemWriter<DeviceEventLogFact> jdbcWriter) {
        return stepBuilderFactory.get("processFile")
                .<EventLogODL, DeviceEventLogFact>chunk(chunkSize)
                .reader(csvRecordReader)
                .processor(deviceEventProcessor)
                .writer(jdbcWriter)
                .build();
    }
}
