package com.scalabledataarchitecture.etl.steps;

import com.scalabledataarchitecture.etl.model.DeviceEventLogFact;
import com.scalabledataarchitecture.etl.model.EventLogODL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class DeviceEventLogMapper {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public DeviceEventLogFact map(EventLogODL eventLogODL) {
        String sqlForMapper = createQuery(eventLogODL);
        return jdbcTemplate.queryForObject(sqlForMapper, new BeanPropertyRowMapper<>(DeviceEventLogFact.class));
    }


    private String createQuery(EventLogODL eventLogODL) {
        return String.format("WITH DEVICE_DM AS\n" +
                "\t(SELECT '%s' AS eventLogId,\n" +
                "\t\t\tdevice_dimension.deviceid\n" +
                "\t\tFROM chapter4.device_dimension\n" +
                "\t\tWHERE deviceSerialNo = '%s'),\n" +
                "\tEVENT_DM AS\n" +
                "\t(SELECT '%s' AS eventLogId,\n" +
                "\t\t\tevent_dimension.eventid\n" +
                "\t\tFROM chapter4.event_dimension\n" +
                "\t\tWHERE eventCd = '%s'),\n" +
                "\tHOUR_DM AS\n" +
                "\t(SELECT '%s' AS eventLogId,\n" +
                "\t\t\thour_dimension.hourid\n" +
                "\t\tFROM chapter4.hour_dimension\n" +
                "\t\tWHERE hour_of_day = EXTRACT(HOUR FROM  TIMESTAMP'%s'))\n" +
                "\tSELECT DEVICE_DM.eventLogId AS eventLogId,\n" +
                "\tDEVICE_DM.deviceId AS deviceId,\n" +
                "\tEVENT_DM.eventId AS eventId,\n" +
                "\tHOUR_DM.hourId AS hourId,\n" +
                "\tEXTRACT(MONTH FROM TIMESTAMP'%s') AS monthId,\n" +
                "\tEXTRACT(QUARTER FROM TIMESTAMP'%s') AS quarterId,\n" +
                "\t'%s' AS EVENTTIMESTAMP,\n" +
                "\t'%s' AS closureStatus, %d AS closureDuration\n" +
                "FROM DEVICE_DM\n" +
                "JOIN EVENT_DM ON DEVICE_DM.eventLogId = EVENT_DM.eventLogId\n" +
                "JOIN HOUR_DM ON DEVICE_DM.eventLogId = HOUR_DM.eventLogId", eventLogODL.getIncidentNumber(), eventLogODL.getDeviceSerialNum(), eventLogODL.getIncidentNumber(), eventLogODL.getEventCode(), eventLogODL.getIncidentNumber(), eventLogODL.getLoggedTime(), eventLogODL.getLoggedTime(), eventLogODL.getLoggedTime(), eventLogODL.getLoggedTime(), eventLogODL.getClosureTime() != null && !eventLogODL.getClosureTime().isEmpty(), eventLogODL.getClosureTime() != null && !eventLogODL.getClosureTime().isEmpty() ? Timestamp.valueOf(eventLogODL.getClosureTime()).getTime() - Timestamp.valueOf(eventLogODL.getLoggedTime()).getTime() : -1);
    }

}
