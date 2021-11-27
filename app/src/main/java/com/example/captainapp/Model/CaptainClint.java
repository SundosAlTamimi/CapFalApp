package com.example.captainapp.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class CaptainClint {

    //String captainName,String captainPhoneNo,String captainId,String TransferId,
    //            String status ,String captainRate,String currentLocation,String clientName ,String clientNo,String date, String timeToArraive

    private String CAPTAIN_NAME;
    private String CAPTAIN_NO;
    private String STATUS;
    private String CAPTAIN_RATE;
    private String CURRENT_LOCATION;
    private String CLIENT_NAME;
    private String CLIENT_NO;
    private String CURRENT_DATE;
    private String SERIAL;

    public CaptainClint() {
    }

    public CaptainClint(String CAPTAIN_NAME, String CAPTAIN_NO,
                        String STATUS, String CAPTAIN_RATE, String CURRENT_LOCATION,
                        String CLIENT_NAME, String CLIENT_NO) {
        this.CAPTAIN_NAME = CAPTAIN_NAME;
        this.CAPTAIN_NO = CAPTAIN_NO;
        this.STATUS = STATUS;
        this.CAPTAIN_RATE = CAPTAIN_RATE;
        this.CURRENT_LOCATION = CURRENT_LOCATION;
        this.CLIENT_NAME = CLIENT_NAME;
        this.CLIENT_NO = CLIENT_NO;
    }

    public String getCAPTAIN_NAME() {
        return CAPTAIN_NAME;
    }

    public void setCAPTAIN_NAME(String CAPTAIN_NAME) {
        this.CAPTAIN_NAME = CAPTAIN_NAME;
    }

    public String getCAPTAIN_NO() {
        return CAPTAIN_NO;
    }

    public void setCAPTAIN_NO(String CAPTAIN_NO) {
        this.CAPTAIN_NO = CAPTAIN_NO;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCAPTAIN_RATE() {
        return CAPTAIN_RATE;
    }

    public void setCAPTAIN_RATE(String CAPTAIN_RATE) {
        this.CAPTAIN_RATE = CAPTAIN_RATE;
    }

    public String getCURRENT_LOCATION() {
        return CURRENT_LOCATION;
    }

    public void setCURRENT_LOCATION(String CURRENT_LOCATION) {
        this.CURRENT_LOCATION = CURRENT_LOCATION;
    }

    public String getCLIENT_NAME() {
        return CLIENT_NAME;
    }

    public void setCLIENT_NAME(String CLIENT_NAME) {
        this.CLIENT_NAME = CLIENT_NAME;
    }

    public String getCLIENT_NO() {
        return CLIENT_NO;
    }

    public void setCLIENT_NO(String CLIENT_NO) {
        this.CLIENT_NO = CLIENT_NO;
    }

    public String getCURRENT_DATE() {
        return CURRENT_DATE;
    }

    public void setCURRENT_DATE(String CURRENT_DATE) {
        this.CURRENT_DATE = CURRENT_DATE;
    }

    public String getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(String SERIAL) {
        this.SERIAL = SERIAL;
    }

    public JSONObject getJsonClint (){
        JSONObject jsonObject=new JSONObject();

        try {

            jsonObject.put("CAPTAIN_NAME","'"+CAPTAIN_NAME+"'");
            jsonObject.put("CAPTAIN_NO","'"+convertToEnglish(CAPTAIN_NO)+"'");
            jsonObject.put("STATUS","'"+convertToEnglish(STATUS)+"'");
            jsonObject.put("CAPTAIN_RATE","'"+CAPTAIN_RATE+"'");
            jsonObject.put("CURRENT_LOCATION","'"+convertToEnglish(CURRENT_LOCATION)+"'");
            jsonObject.put("CLIENT_NAME","'"+CLIENT_NAME+"'");
            jsonObject.put("CLIENT_NO","'"+convertToEnglish(CLIENT_NO)+"'");
            jsonObject.put("CURRENT_DATE","'"+convertToEnglish(CURRENT_DATE)+"'");
            jsonObject.put("SERIAL","'"+convertToEnglish(SERIAL)+"'");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  jsonObject;
    }
    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫","."));
        return newValue;
    }
}
