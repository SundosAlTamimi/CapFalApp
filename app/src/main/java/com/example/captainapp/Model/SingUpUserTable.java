package com.example.captainapp.Model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class SingUpUserTable {

    @SerializedName("id")
    private  String id;
    @SerializedName("PHONE_NO")
    private  String PHONE_NO;
    @SerializedName("PASSWORD")
    private String PASSWORD;
    @SerializedName("PROFILE_PIC")
    private  String PROFILE_PIC;
    @SerializedName("IDENTITY_PIC")
    private  String IDENTITY_PIC;
    @SerializedName("DRIVING_LICENCE_PIC")
    private  String DRIVING_LICENCE_PIC;
    @SerializedName("PERMIT_PIC")
    private  String PERMIT_PIC;
    @SerializedName("CAR_LICENCE_PIC")
    private  String CAR_LICENCE_PIC;
    @SerializedName("PAYMENT_300_PIC")
    private  String PAYMENT_300_PIC;
    @SerializedName("PAYMENT_50_PIC")
    private  String PAYMENT_50_PIC;
    @SerializedName("DESEASE_FREE_PIC")
    private  String DESEASE_FREE_PIC;
    @SerializedName("CRIMINAL_RECORE_PIC")
    private  String CRIMINAL_RECORE_PIC;
    @SerializedName("userName")
    private  String userName;

    @SerializedName("ActivityUser")
    private int ActivityUser;


    private  Bitmap PROFILE_PIC_Bitmap;
    private  Bitmap IDENTITY_PIC_Bitmap;
    private  Bitmap DRIVING_LICENCE_PIC_Bitmap;
    private  Bitmap PERMIT_PIC_Bitmap;
    private  Bitmap CAR_LICENCE_PIC_Bitmap;
    private  Bitmap PAYMENT_300_PIC_Bitmap;
    private  Bitmap PAYMENT_50_PIC_Bitmap;
    private  Bitmap DESEASE_FREE_PIC_Bitmap;
    private  Bitmap CRIMINAL_RECORE_PIC_Bitmap;

    public SingUpUserTable() {
    }

    public SingUpUserTable(String PHONE_NO, String PASSWORD, String PROFILE_PIC, String IDENTITY_PIC,
                           String DRIVING_LICENCE_PIC, String PERMIT_PIC, String CAR_LICENCE_PIC, String PAYMENT_300_PIC,
                           String PAYMENT_50_PIC, String DESEASE_FREE_PIC, String CRIMINAL_RECORE_PIC) {
        this.PHONE_NO = PHONE_NO;
        this.PASSWORD = PASSWORD;
        this.PROFILE_PIC = PROFILE_PIC;
        this.IDENTITY_PIC = IDENTITY_PIC;
        this.DRIVING_LICENCE_PIC = DRIVING_LICENCE_PIC;
        this.PERMIT_PIC = PERMIT_PIC;
        this.CAR_LICENCE_PIC = CAR_LICENCE_PIC;
        this.PAYMENT_300_PIC = PAYMENT_300_PIC;
        this.PAYMENT_50_PIC = PAYMENT_50_PIC;
        this.DESEASE_FREE_PIC = DESEASE_FREE_PIC;
        this.CRIMINAL_RECORE_PIC = CRIMINAL_RECORE_PIC;
    }

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getPROFILE_PIC() {
        return PROFILE_PIC;
    }

    public void setPROFILE_PIC(String PROFILE_PIC) {
        this.PROFILE_PIC = PROFILE_PIC;
    }

    public String getIDENTITY_PIC() {
        return IDENTITY_PIC;
    }

    public void setIDENTITY_PIC(String IDENTITY_PIC) {
        this.IDENTITY_PIC = IDENTITY_PIC;
    }

    public String getDRIVING_LICENCE_PIC() {
        return DRIVING_LICENCE_PIC;
    }

    public void setDRIVING_LICENCE_PIC(String DRIVING_LICENCE_PIC) {
        this.DRIVING_LICENCE_PIC = DRIVING_LICENCE_PIC;
    }

    public String getPERMIT_PIC() {
        return PERMIT_PIC;
    }

    public void setPERMIT_PIC(String PERMIT_PIC) {
        this.PERMIT_PIC = PERMIT_PIC;
    }

    public String getCAR_LICENCE_PIC() {
        return CAR_LICENCE_PIC;
    }

    public void setCAR_LICENCE_PIC(String CAR_LICENCE_PIC) {
        this.CAR_LICENCE_PIC = CAR_LICENCE_PIC;
    }

    public String getPAYMENT_300_PIC() {
        return PAYMENT_300_PIC;
    }

    public void setPAYMENT_300_PIC(String PAYMENT_300_PIC) {
        this.PAYMENT_300_PIC = PAYMENT_300_PIC;
    }

    public String getPAYMENT_50_PIC() {
        return PAYMENT_50_PIC;
    }

    public void setPAYMENT_50_PIC(String PAYMENT_50_PIC) {
        this.PAYMENT_50_PIC = PAYMENT_50_PIC;
    }

    public String getDESEASE_FREE_PIC() {
        return DESEASE_FREE_PIC;
    }

    public void setDESEASE_FREE_PIC(String DESEASE_FREE_PIC) {
        this.DESEASE_FREE_PIC = DESEASE_FREE_PIC;
    }

    public String getCRIMINAL_RECORE_PIC() {
        return CRIMINAL_RECORE_PIC;
    }

    public void setCRIMINAL_RECORE_PIC(String CRIMINAL_RECORE_PIC) {
        this.CRIMINAL_RECORE_PIC = CRIMINAL_RECORE_PIC;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getActivityUser() {
        return ActivityUser;
    }

    public Bitmap getPROFILE_PIC_Bitmap() {
        return PROFILE_PIC_Bitmap;
    }

    public void setPROFILE_PIC_Bitmap(Bitmap PROFILE_PIC_Bitmap) {
        this.PROFILE_PIC_Bitmap = PROFILE_PIC_Bitmap;
    }

    public Bitmap getIDENTITY_PIC_Bitmap() {
        return IDENTITY_PIC_Bitmap;
    }

    public void setIDENTITY_PIC_Bitmap(Bitmap IDENTITY_PIC_Bitmap) {
        this.IDENTITY_PIC_Bitmap = IDENTITY_PIC_Bitmap;
    }

    public Bitmap getDRIVING_LICENCE_PIC_Bitmap() {
        return DRIVING_LICENCE_PIC_Bitmap;
    }

    public void setDRIVING_LICENCE_PIC_Bitmap(Bitmap DRIVING_LICENCE_PIC_Bitmap) {
        this.DRIVING_LICENCE_PIC_Bitmap = DRIVING_LICENCE_PIC_Bitmap;
    }

    public Bitmap getPERMIT_PIC_Bitmap() {
        return PERMIT_PIC_Bitmap;
    }

    public void setPERMIT_PIC_Bitmap(Bitmap PERMIT_PIC_Bitmap) {
        this.PERMIT_PIC_Bitmap = PERMIT_PIC_Bitmap;
    }

    public Bitmap getCAR_LICENCE_PIC_Bitmap() {
        return CAR_LICENCE_PIC_Bitmap;
    }

    public void setCAR_LICENCE_PIC_Bitmap(Bitmap CAR_LICENCE_PIC_Bitmap) {
        this.CAR_LICENCE_PIC_Bitmap = CAR_LICENCE_PIC_Bitmap;
    }

    public Bitmap getPAYMENT_300_PIC_Bitmap() {
        return PAYMENT_300_PIC_Bitmap;
    }

    public void setPAYMENT_300_PIC_Bitmap(Bitmap PAYMENT_300_PIC_Bitmap) {
        this.PAYMENT_300_PIC_Bitmap = PAYMENT_300_PIC_Bitmap;
    }

    public Bitmap getPAYMENT_50_PIC_Bitmap() {
        return PAYMENT_50_PIC_Bitmap;
    }

    public void setPAYMENT_50_PIC_Bitmap(Bitmap PAYMENT_50_PIC_Bitmap) {
        this.PAYMENT_50_PIC_Bitmap = PAYMENT_50_PIC_Bitmap;
    }

    public Bitmap getDESEASE_FREE_PIC_Bitmap() {
        return DESEASE_FREE_PIC_Bitmap;
    }

    public void setDESEASE_FREE_PIC_Bitmap(Bitmap DESEASE_FREE_PIC_Bitmap) {
        this.DESEASE_FREE_PIC_Bitmap = DESEASE_FREE_PIC_Bitmap;
    }

    public Bitmap getCRIMINAL_RECORE_PIC_Bitmap() {
        return CRIMINAL_RECORE_PIC_Bitmap;
    }

    public void setCRIMINAL_RECORE_PIC_Bitmap(Bitmap CRIMINAL_RECORE_PIC_Bitmap) {
        this.CRIMINAL_RECORE_PIC_Bitmap = CRIMINAL_RECORE_PIC_Bitmap;
    }

    public void setActivityUser(int activityUser) {
        ActivityUser = activityUser;
    }

    public  JSONObject getSingUpJson (){
        JSONObject jsonObject=new JSONObject();

        try {

            //String phoneNo, String userName,
            //            String Password, int Activiat, String PROFILE_PIC
            //           , String IDENTITY_PIC, String DRIVING_LICENCE_PIC, String PERMIT_PIC,
            //            String CAR_LICENCE_PIC ,String PAYMENT_300_PIC,String PAYMENT_50_PIC ,String DESEASE_FREE_PIC
            //            ,String CRIMINAL_RECORE_PIC

            jsonObject.put("phoneNo",convertToEnglish(PHONE_NO));
            jsonObject.put("userName",convertToEnglish(userName));
            jsonObject.put("Password",convertToEnglish(PASSWORD));
            jsonObject.put("PROFILE_PIC",PROFILE_PIC);
            jsonObject.put("DRIVING_LICENCE_PIC",DRIVING_LICENCE_PIC);
            jsonObject.put("PERMIT_PIC",PERMIT_PIC);
            jsonObject.put("CAR_LICENCE_PIC",CAR_LICENCE_PIC);
            jsonObject.put("PAYMENT_300_PIC",PAYMENT_300_PIC);
            jsonObject.put("PAYMENT_50_PIC",PAYMENT_50_PIC);
            jsonObject.put("DESEASE_FREE_PIC",DESEASE_FREE_PIC);
            jsonObject.put("CRIMINAL_RECORE_PIC",CRIMINAL_RECORE_PIC);
            jsonObject.put("IDENTITY_PIC",IDENTITY_PIC);

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
