package com.scalabaledataarch.realtime.model;

import com.scalabaledataarch.realtime.model.CreditRecord;

import java.util.List;

public class MLRequest {
    private String id;
    private String genderCode;
    private String flagOwnCar;
    private String flagOwnRealty;
    private int cntChildren;
    private double amtIncomeTotal;
    private String nameFamilyStatus;
    private String nameHousingType;
    private boolean employmentStatus;
    private long monthsEmployed;
    private List<CreditRecord> creditRecords;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public String getFlagOwnCar() {
        return flagOwnCar;
    }

    public void setFlagOwnCar(String flagOwnCar) {
        this.flagOwnCar = flagOwnCar;
    }

    public String getFlagOwnRealty() {
        return flagOwnRealty;
    }

    public void setFlagOwnRealty(String flagOwnRealty) {
        this.flagOwnRealty = flagOwnRealty;
    }

    public int getCntChildren() {
        return cntChildren;
    }

    public void setCntChildren(int cntChildren) {
        this.cntChildren = cntChildren;
    }

    public double getAmtIncomeTotal() {
        return amtIncomeTotal;
    }

    public void setAmtIncomeTotal(double amtIncomeTotal) {
        this.amtIncomeTotal = amtIncomeTotal;
    }

    public String getNameFamilyStatus() {
        return nameFamilyStatus;
    }

    public void setNameFamilyStatus(String nameFamilyStatus) {
        this.nameFamilyStatus = nameFamilyStatus;
    }

    public String getNameHousingType() {
        return nameHousingType;
    }

    public void setNameHousingType(String nameHousingType) {
        this.nameHousingType = nameHousingType;
    }

    public boolean isEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(boolean employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public long getMonthsEmployed() {
        return monthsEmployed;
    }

    public void setMonthsEmployed(long monthsEmployed) {
        this.monthsEmployed = monthsEmployed;
    }

    public List<CreditRecord> getCreditRecords() {
        return creditRecords;
    }

    public void setCreditRecords(List<CreditRecord> creditRecords) {
        this.creditRecords = creditRecords;
    }
}
