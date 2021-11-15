package com.scalabledataarchitecture.etl.steps;

import com.scalabledataarchitecture.etl.model.DeviceEventLogFact;
import com.scalabledataarchitecture.etl.model.EventLogODL;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceEventProcessor implements ItemProcessor<EventLogODL, DeviceEventLogFact> {

    @Autowired
    DeviceEventLogMapper deviceEventLogMapper;

    @Override
    public DeviceEventLogFact process(EventLogODL eventLogODL) throws Exception {
        return deviceEventLogMapper.map(eventLogODL);
    }
}
