package com.scalabledataarchitecture.etl.model;

import java.sql.Timestamp;

public class DeviceEventLogFact {
    private String eventLogId;
    private int deviceId;
    private int eventId;
    private int hourId;
    private int monthId;
    private int quarterId;
    private long closureDuration;
    private Timestamp eventTimestamp;
    private boolean closureStatus;

    public String getEventLogId() {
        return eventLogId;
    }

    public void setEventLogId(String eventLogId) {
        this.eventLogId = eventLogId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getHourId() {
        return hourId;
    }

    public void setHourId(int hourId) {
        this.hourId = hourId;
    }

    public int getMonthId() {
        return monthId;
    }

    public void setMonthId(int monthId) {
        this.monthId = monthId;
    }

    public int getQuarterId() {
        return quarterId;
    }

    public void setQuarterId(int quarterId) {
        this.quarterId = quarterId;
    }

    public long getClosureDuration() {
        return closureDuration;
    }

    public void setClosureDuration(long closureDuration) {
        this.closureDuration = closureDuration;
    }

    public Timestamp getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(Timestamp eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public boolean isClosureStatus() {
        return closureStatus;
    }

    public void setClosureStatus(boolean closureStatus) {
        this.closureStatus = closureStatus;
    }
}
