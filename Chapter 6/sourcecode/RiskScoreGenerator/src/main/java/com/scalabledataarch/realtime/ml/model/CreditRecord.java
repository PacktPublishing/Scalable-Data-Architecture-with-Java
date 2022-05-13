package com.scalabledataarch.realtime.ml.model;

public class CreditRecord {
    //private String id;
    private long months_balance;
    private String status;

    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

    public long getMonths_balance() {
        return months_balance;
    }

    public void setMonths_balance(long months_balance) {
        this.months_balance = months_balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
