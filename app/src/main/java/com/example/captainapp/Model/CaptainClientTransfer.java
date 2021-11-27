package com.example.captainapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CaptainClientTransfer {
    //  {
    //        "id": 1,
    //        "ClientName": "rawan",
    //        "ClientPhoneNo": " 962786812709",
    //        "ClientId": 1,//        "FromLoc": "lat/lng: (32.008451,36.0157778)",
    //        "ToLocation": "lat/lng: (32.008451,36.0157778)",
    //        "LocationName": "niyr",
    //        "PaymentType": 0,
    //        "CaptainName": null,
    //        "CaptainId": 0,
    //        "CaptainPhoneNo": null,
    //        "Status": 0,
    //        "Rate": "0",
    //        "TimeOfArraive": null,
    //        "DateOfTranse": null,
    //        "TimeIn": null,
    //        "Timeout": null,
    //        "TimeParkIn": null,
    //        "TimeParkOut": null
    //    },

    @SerializedName("id")
    private int id;

    @SerializedName("ClientName")
    private String ClientName;

    @SerializedName("ClientPhoneNo")
    private String ClientPhoneNo;

    @SerializedName("ClientId")
    private int ClientId;
    @SerializedName("FromLoc")
    private String FromLoc;
    @SerializedName("ToLocation")
    private String ToLocation;
    @SerializedName("LocationName")
    private String LocationName;
    @SerializedName("PaymentType")
    private String PaymentType;
    @SerializedName("CaptainName")
    private String CaptainName;
    @SerializedName("CaptainId")
    private int CaptainId;
    @SerializedName("CaptainPhoneNo")
    private String CaptainPhoneNo;
    @SerializedName("Status")
    private String Status;
    @SerializedName("Rate")
    private String Rate;
    @SerializedName("TimeOfArraive")
    private String TimeOfArraive;
    @SerializedName("DateOfTranse")
    private String DateOfTranse;
    @SerializedName("TimeIn")
    private String TimeIn;
    @SerializedName("Timeout")
    private String Timeout;
    @SerializedName("TimeParkIn")
    private String TimeParkIn;
    @SerializedName("TimeParkOut")
    private String TimeParkOut;
    @SerializedName("clientsInfo")
    private InformationOfClint clients;

    @SerializedName("ParkingLocation")
    private String ParkingLocation;

    @SerializedName("CaptainClientTransfer")
    private CaptainClientTransfer CaptainClientTransfer;

    private List<CaptainClientTransfer> orderList;

    public CaptainClientTransfer() {
    }

    public CaptainClientTransfer(int id, String clientName, String clientPhoneNo, int clientId, String fromLoc, String toLocation, String locationName,
                                 String paymentType, String captainName, int captainId, String captainPhoneNo, String status, String rate, String timeOfArraive,
                                 String dateOfTranse, String timeIn, String timeout, String timeParkIn, String timeParkOut) {
        this.id = id;
        ClientName = clientName;
        ClientPhoneNo = clientPhoneNo;
        ClientId = clientId;
        FromLoc = fromLoc;
        ToLocation = toLocation;
        LocationName = locationName;
        PaymentType = paymentType;
        CaptainName = captainName;
        CaptainId = captainId;
        CaptainPhoneNo = captainPhoneNo;
        Status = status;
        Rate = rate;
        TimeOfArraive = timeOfArraive;
        DateOfTranse = dateOfTranse;
        TimeIn = timeIn;
        Timeout = timeout;
        TimeParkIn = timeParkIn;
        TimeParkOut = timeParkOut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getClientPhoneNo() {
        return ClientPhoneNo;
    }

    public void setClientPhoneNo(String clientPhoneNo) {
        ClientPhoneNo = clientPhoneNo;
    }

    public int getClientId() {
        return ClientId;
    }

    public void setClientId(int clientId) {
        ClientId = clientId;
    }

    public String getFromLoc() {
        return FromLoc;
    }

    public void setFromLoc(String fromLoc) {
        FromLoc = fromLoc;
    }

    public String getToLocation() {
        return ToLocation;
    }

    public void setToLocation(String toLocation) {
        ToLocation = toLocation;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public String getCaptainName() {
        return CaptainName;
    }

    public void setCaptainName(String captainName) {
        CaptainName = captainName;
    }

    public int getCaptainId() {
        return CaptainId;
    }

    public void setCaptainId(int captainId) {
        CaptainId = captainId;
    }

    public String getCaptainPhoneNo() {
        return CaptainPhoneNo;
    }

    public void setCaptainPhoneNo(String captainPhoneNo) {
        CaptainPhoneNo = captainPhoneNo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getTimeOfArraive() {
        return TimeOfArraive;
    }

    public void setTimeOfArraive(String timeOfArraive) {
        TimeOfArraive = timeOfArraive;
    }

    public String getDateOfTranse() {
        return DateOfTranse;
    }

    public void setDateOfTranse(String dateOfTranse) {
        DateOfTranse = dateOfTranse;
    }

    public String getTimeIn() {
        return TimeIn;
    }

    public void setTimeIn(String timeIn) {
        TimeIn = timeIn;
    }

    public String getTimeout() {
        return Timeout;
    }

    public void setTimeout(String timeout) {
        Timeout = timeout;
    }

    public String getTimeParkIn() {
        return TimeParkIn;
    }

    public void setTimeParkIn(String timeParkIn) {
        TimeParkIn = timeParkIn;
    }

    public String getTimeParkOut() {
        return TimeParkOut;
    }

    public void setTimeParkOut(String timeParkOut) {
        TimeParkOut = timeParkOut;
    }

    public List<CaptainClientTransfer> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<CaptainClientTransfer> orderList) {
        this.orderList = orderList;
    }

    public InformationOfClint getClients() {
        return clients;
    }

    public void setClients(InformationOfClint clients) {
        this.clients = clients;
    }

    public com.example.captainapp.Model.CaptainClientTransfer getCaptainClientTransfer() {
        return CaptainClientTransfer;
    }

    public void setCaptainClientTransfer(com.example.captainapp.Model.CaptainClientTransfer captainClientTransfer) {
        CaptainClientTransfer = captainClientTransfer;
    }

    public String getParkingLocation() {
        return ParkingLocation;
    }

    public void setParkingLocation(String parkingLocation) {
        ParkingLocation = parkingLocation;
    }
}
