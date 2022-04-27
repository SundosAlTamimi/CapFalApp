package com.example.captainapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CaptainDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =4;//version Db
    private static final String DATABASE_Name = "CaptainDBase";//name Db

    static SQLiteDatabase Idb;

    //___________________________________________________________________________________
    private static final String SETTING_TABLE = "SETTING_TABLE";

    private static final String ID_RAW = "ID_RAW";

    //___________________________________________________________________________________
    private static final String SETTING_IP_TABLE = "SETTING_IP_TABLE";

    private static final String IP_RAW = "IP_RAW";
    private static final String ACTIVITY = "ACTIVITY";

    //___________________________________________________________________________________
    private static final String USER_TABLE_SERVICE = "USER_TABLE_SERVICE";

    private static final String USER_ID = "USER_ID";
    private static final String USER_NAME = "USER_NAME";
    private static final String PHONE_NO = "PHONE_NO";
    private static final String ON_OFF_SERVICE = "ON_OFF_SERVICE";


    //_________________________________________________________________________________

    public CaptainDatabase(Context context) {
        super(context, DATABASE_Name, null, DATABASE_VERSION);
    }
    //__________________________________________________________________________________


    @Override
    public void onCreate(SQLiteDatabase Idb) {

        String CREATE_TABLE_ITEM_CARD = "CREATE TABLE " + SETTING_TABLE + "("
                + ID_RAW + " NVARCHAR" + ")";
        Idb.execSQL(CREATE_TABLE_ITEM_CARD);


        String CREATE_TABLE_SETTING_IP = "CREATE TABLE " + SETTING_IP_TABLE + "("
                + IP_RAW + " NVARCHAR" + ","
                + ACTIVITY + " NVARCHAR" + ")";
        Idb.execSQL(CREATE_TABLE_SETTING_IP);


        String CREATE_TABLE_USER_TABLE = "CREATE TABLE " + USER_TABLE_SERVICE + "("
                + USER_ID + " NVARCHAR" + ","
                + USER_NAME + " NVARCHAR" + ","
                + PHONE_NO + " NVARCHAR" + ","
                + ON_OFF_SERVICE + " NVARCHAR" + ")";
        Idb.execSQL(CREATE_TABLE_USER_TABLE);
//=========================================================================================

//=========================================================================================

    }

    @Override
    public void onUpgrade(SQLiteDatabase Idb, int oldVersion, int newVersion) {
try{
    String CREATE_TABLE_ITEM_CARD = "CREATE TABLE " + SETTING_TABLE + "("
            + ID_RAW + " NVARCHAR" + ")";
    Idb.execSQL(CREATE_TABLE_ITEM_CARD);
}catch (Exception r){

}


        try{
            String CREATE_TABLE_SETTING_IP = "CREATE TABLE " + SETTING_IP_TABLE + "("
                    + IP_RAW + " NVARCHAR" + ","
                    + ACTIVITY + " NVARCHAR" + ")";
            Idb.execSQL(CREATE_TABLE_SETTING_IP);
        }catch (Exception e){

        }


        try{
            String CREATE_TABLE_USER_TABLE = "CREATE TABLE " + USER_TABLE_SERVICE + "("
                    + USER_ID + " NVARCHAR" + ","
                    + USER_NAME + " NVARCHAR" + ","
                    + PHONE_NO + " NVARCHAR" + ","
                    + ON_OFF_SERVICE + " NVARCHAR" + ")";
            Idb.execSQL(CREATE_TABLE_USER_TABLE);
        }catch (Exception e){

        }

    }


    public void addIpSetting(String ip,String active) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(IP_RAW,ip );
        values.put(ACTIVITY,active );
        Idb.insert(SETTING_IP_TABLE, null, values);
        Idb.close();
    }

    public String getAllIPSetting() {

        String selectQuery = "SELECT  * FROM " + SETTING_IP_TABLE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);
        String idRaw="";
        if (cursor.moveToFirst()) {
            do {

                idRaw=cursor.getString(0);

            } while (cursor.moveToNext());
        }
        return idRaw;
    }


    public void addUserService(UserService userService) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_ID,userService.getUserid() );
        values.put(USER_NAME,userService.getUserName() );
        values.put(PHONE_NO,userService.getUserPhoneNo() );
        values.put(ON_OFF_SERVICE,userService.getOnOff() );

        Idb.insert(USER_TABLE_SERVICE, null, values);
        Idb.close();
    }

    public String getAllActivitySetting() {

        String selectQuery = "SELECT  * FROM " + SETTING_IP_TABLE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);
        String idRaw="";
        if (cursor.moveToFirst()) {
            do {

                idRaw=cursor.getString(1);

            } while (cursor.moveToNext());
        }
        return idRaw;
    }

    public String getAllaCTIVESetting() {

        String selectQuery = "SELECT  * FROM " + SETTING_IP_TABLE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);
        String idRaw="";
        if (cursor.moveToFirst()) {
            do {

                idRaw=cursor.getString(1);

            } while (cursor.moveToNext());
        }
        return idRaw;
    }

    public void addSetting(String id) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID_RAW,id );

        Idb.insert(SETTING_TABLE, null, values);
        Idb.close();
    }

    public String getAllSetting() {

        String selectQuery = "SELECT  * FROM " + SETTING_TABLE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);
        String idRaw="";
        if (cursor.moveToFirst()) {
            do {

                idRaw=cursor.getString(0);

            } while (cursor.moveToNext());
        }
        return idRaw;
    }



    public void delete() {
        try {
            Idb = this.getWritableDatabase();
            Idb.execSQL("DELETE FROM " + SETTING_TABLE); //delete all rows in a table
            Idb.close();
        }catch (Exception r){

        }
    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫","."));
        return newValue;
    }

    public void updateiP(String ipOld, String IP) {
        Idb = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(IP_RAW, IP);
        Idb.update(SETTING_IP_TABLE, values, IP_RAW + " = '" + ipOld + "'", null);
    }

    public void updateaCTIVE(String ipOld, String ACTIVITYA) {
        Idb = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ACTIVITY, ACTIVITYA);
        Idb.update(SETTING_IP_TABLE, values, IP_RAW + " = '" + ipOld + "'", null);
    }


    public String getAllUser() {

        String selectQuery = "SELECT  * FROM " + USER_TABLE_SERVICE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);
        String idRaw="";
        if (cursor.moveToFirst()) {
            do {

                idRaw=cursor.getString(0);

            } while (cursor.moveToNext());
        }
        return idRaw;
    }

    public String getAllUserOnOff() {

        String selectQuery = "SELECT  * FROM " + USER_TABLE_SERVICE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);
        String idRaw="";
        if (cursor.moveToFirst()) {
            do {

                idRaw=cursor.getString(3);

            } while (cursor.moveToNext());
        }
        return idRaw;
    }

    public void deleteUser() {
        Idb= this.getWritableDatabase();
        Idb.execSQL("DELETE FROM "+USER_TABLE_SERVICE); //delete all rows in a table
        Idb.close();
    }

    public void updateUser(String idOld, String ac) {
        Idb = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ON_OFF_SERVICE, ac);
        Idb.update(USER_TABLE_SERVICE, values, USER_ID + " = '" + idOld + "'", null);
    }
}
