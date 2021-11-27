package com.example.captainapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InformationOfClint implements Serializable {

    //"clients":{"id":3028,"UserName":"t","Password":"t"
    // ,"Email":"t","PhoneNo":"t","CarType":"h","CarModel":"b","CarColor":"n",
    // "CarLot":"b","CarPic":"null","EndRawId":""}}


    @SerializedName("id")
    private String id;

    @SerializedName("UserName")
private String USERNAME;


    @SerializedName("Password")
    private String PASSWORD;


    @SerializedName("Email")
    private String E_MAIL;

    @SerializedName("PhoneNo")
    private String PHONE_NO;

    @SerializedName("CarType")
    private String CAR_TYPE;

    @SerializedName("CarModel")
    private String CAR_MODEL;

    @SerializedName("CarColor")
    private String CAR_COLOR;

    @SerializedName("CarLot")
    private String CAR_LOT;

    @SerializedName("CarPic")
    private String TIME;

    @SerializedName("EndRawId")
    private String DATE_;

//    @SerializedName("LATITUDE")
//    private String LATITUDE;
//
//    @SerializedName("LONGITUDE")
//    private String LONGITUDE;
//
//
//    @SerializedName("STATUS")
//    private String STATUS;
//
//
//
//    @SerializedName("VALET_NAME")
//    private String VALET_NAME;
//
//
//    @SerializedName("VALET_NO")
//    private String VALET_NO;
//
//    @SerializedName("CAPTAINS_STATUS")
//private  String CAPTAINS_STATUS;
//    @SerializedName("SERIAL")
//    private String SERIAL;
//
//    @SerializedName("REQUEST_VALET_LOG")
//    private List<InformationOfClint> REQUEST_VALET_LOG;
//
//    private String arriveTime;
//
//private  String updateCaptainStatus;

    public InformationOfClint() {

    }

    public InformationOfClint(String USERNAME, String PASSWORD, String e_MAIL, String PHONE_NO, String CAR_TYPE, String CAR_MODEL, String CAR_COLOR, String CAR_LOT, String TIME, String DATE_) {
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        E_MAIL = e_MAIL;
        this.PHONE_NO = PHONE_NO;
        this.CAR_TYPE = CAR_TYPE;
        this.CAR_MODEL = CAR_MODEL;
        this.CAR_COLOR = CAR_COLOR;
        this.CAR_LOT = CAR_LOT;
        this.TIME = TIME;
        this.DATE_ = DATE_;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getE_MAIL() {
        return E_MAIL;
    }

    public void setE_MAIL(String e_MAIL) {
        E_MAIL = e_MAIL;
    }

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }

    public String getCAR_TYPE() {
        return CAR_TYPE;
    }

    public void setCAR_TYPE(String CAR_TYPE) {
        this.CAR_TYPE = CAR_TYPE;
    }

    public String getCAR_MODEL() {
        return CAR_MODEL;
    }

    public void setCAR_MODEL(String CAR_MODEL) {
        this.CAR_MODEL = CAR_MODEL;
    }

    public String getCAR_COLOR() {
        return CAR_COLOR;
    }

    public void setCAR_COLOR(String CAR_COLOR) {
        this.CAR_COLOR = CAR_COLOR;
    }

    public String getCAR_LOT() {
        return CAR_LOT;
    }

    public void setCAR_LOT(String CAR_LOT) {
        this.CAR_LOT = CAR_LOT;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getDATE_() {
        return DATE_;
    }

    public void setDATE_(String DATE_) {
        this.DATE_ = DATE_;
    }


}
