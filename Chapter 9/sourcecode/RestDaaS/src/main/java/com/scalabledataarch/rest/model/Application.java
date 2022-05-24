package com.scalabledataarch.rest.model;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@QueryEntity
@Document(collection = "newloanrequest")
public class Application  {
    @Id
    private String _id;
    private String applicationId;
    private String id;
    private String genderCode;
    private String flagOwnCar;
    private String flagOwnRealty;
    private int cntChildren;
    private double amtIncomeTotal;
    private String nameIncomeType;
    private String nameEducationType;
    private String nameFamilyStatus;
    private String nameHousingType;
    private long daysBirth;
    private long daysEmployed;
    private int flagMobil;
    private int flagWorkPhone;
    private int flagPhone;
    private int flagEmail;
    private String occupationType;
    private int cntFamMembers;
    private double riskScore;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

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

    public String getNameIncomeType() {
        return nameIncomeType;
    }

    public void setNameIncomeType(String nameIncomeType) {
        this.nameIncomeType = nameIncomeType;
    }

    public String getNameEducationType() {
        return nameEducationType;
    }

    public void setNameEducationType(String nameEducationType) {
        this.nameEducationType = nameEducationType;
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

    public long getDaysBirth() {
        return daysBirth;
    }

    public void setDaysBirth(long daysBirth) {
        this.daysBirth = daysBirth;
    }

    public long getDaysEmployed() {
        return daysEmployed;
    }

    public void setDaysEmployed(long daysEmployed) {
        this.daysEmployed = daysEmployed;
    }

    public int getFlagMobil() {
        return flagMobil;
    }

    public void setFlagMobil(int flagMobil) {
        this.flagMobil = flagMobil;
    }

    public int getFlagWorkPhone() {
        return flagWorkPhone;
    }

    public void setFlagWorkPhone(int flagWorkPhone) {
        this.flagWorkPhone = flagWorkPhone;
    }

    public int getFlagPhone() {
        return flagPhone;
    }

    public void setFlagPhone(int flagPhone) {
        this.flagPhone = flagPhone;
    }

    public int getFlagEmail() {
        return flagEmail;
    }

    public void setFlagEmail(int flagEmail) {
        this.flagEmail = flagEmail;
    }

    public String getOccupationType() {
        return occupationType;
    }

    public void setOccupationType(String occupationType) {
        this.occupationType = occupationType;
    }

    public int getCntFamMembers() {
        return cntFamMembers;
    }

    public void setCntFamMembers(int cntFamMembers) {
        this.cntFamMembers = cntFamMembers;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = riskScore;
    }


}
