package com.example.captainapp.Model;

public class ClientOrder {
    //String captainName,String captainPhoneNo,String captainId,String TransferId,
    //            String status ,String captainRate,String currentLocation,String clientName ,String clientNo,String date, String timeToArraive


    private String captainName;
    private String captainPhoneNo;
    private String captainId;
    private String TransferId;
    private String status;
    private String captainRate;
    private String currentLocation;
    private String clientName;
    private String clientNo;
    private String date;
    private String timeToArraive;
    private String clientId;

    public ClientOrder() {

    }

    public ClientOrder(String captainName, String captainPhoneNo, String captainId, String transferId,
                       String status, String captainRate, String currentLocation, String clientName,
                       String clientNo, String date, String timeToArraive) {
        this.captainName = captainName;
        this.captainPhoneNo = captainPhoneNo;
        this.captainId = captainId;
        TransferId = transferId;
        this.status = status;
        this.captainRate = captainRate;
        this.currentLocation = currentLocation;
        this.clientName = clientName;
        this.clientNo = clientNo;
        this.date = date;
        this.timeToArraive = timeToArraive;
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    public String getCaptainPhoneNo() {
        return captainPhoneNo;
    }

    public void setCaptainPhoneNo(String captainPhoneNo) {
        this.captainPhoneNo = captainPhoneNo;
    }

    public String getCaptainId() {
        return captainId;
    }

    public void setCaptainId(String captainId) {
        this.captainId = captainId;
    }

    public String getTransferId() {
        return TransferId;
    }

    public void setTransferId(String transferId) {
        TransferId = transferId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCaptainRate() {
        return captainRate;
    }

    public void setCaptainRate(String captainRate) {
        this.captainRate = captainRate;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientNo() {
        return clientNo;
    }

    public void setClientNo(String clientNo) {
        this.clientNo = clientNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeToArraive() {
        return timeToArraive;
    }

    public void setTimeToArraive(String timeToArraive) {
        this.timeToArraive = timeToArraive;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
