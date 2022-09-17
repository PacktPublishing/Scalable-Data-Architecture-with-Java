package com.scalabaledataarch.realtime.model;

import com.scalabaledataarch.realtime.model.ApplicationEvent;

public class EnrichedApplication  extends ApplicationEvent {

    private double riskScore;

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = riskScore;
    }

    public void setApplicationforEnrichedApplication(ApplicationEvent event){
        setId(event.getId());
        setAmtIncomeTotal(event.getAmtIncomeTotal());
        setCntChildren(event.getCntChildren());
        setDaysBirth(event.getDaysBirth());
        setDaysEmployed(event.getDaysEmployed());
        setCntFamMembers(event.getCntFamMembers());
        setFlagEmail(event.getFlagEmail());
        setFlagMobil(event.getFlagMobil());
        setFlagOwnCar(event.getFlagOwnCar());
        setFlagOwnRealty(event.getFlagOwnRealty());
        setFlagPhone(event.getFlagPhone());
        setFlagWorkPhone(event.getFlagWorkPhone());
        setGenderCode(event.getGenderCode());
        setNameEducationType(event.getNameEducationType());
        setNameFamilyStatus(event.getNameFamilyStatus());
        setNameHousingType(event.getNameHousingType());
        setNameIncomeType(event.getNameIncomeType());
        setOccupationType(event.getOccupationType());
    }
}
