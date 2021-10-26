package com.hiaryabeer.transferapp;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SETTINGS_TABLE")
public class appSettings {
    @ColumnInfo(name = "IP_ADDRESS")
    String IP;
    @ColumnInfo(name = "COMPANYNO")
String CompanyNum;
    @ColumnInfo(name = "YEARS")
String years;
    @ColumnInfo(name = "UPDATEQTY")
String UpdateQTY;
    @ColumnInfo(name = "USERNO")
String userNumber;
    @PrimaryKey(autoGenerate = true)
    public    int SERIALZONE;

    @ColumnInfo(name = "DEVICEID")
    String deviceId;
    public appSettings(String IP, String companyNum, String years, String updateQTY, String userNumber) {
        this.IP = IP;
        CompanyNum = companyNum;
        this.years = years;
        UpdateQTY = updateQTY;
        this.userNumber = userNumber;

    }

    public appSettings() {

    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getCompanyNum() {
        return CompanyNum;
    }

    public void setCompanyNum(String companyNum) {
        CompanyNum = companyNum;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getUpdateQTY() {
        return UpdateQTY;
    }

    public void setUpdateQTY(String updateQTY) {
        UpdateQTY = updateQTY;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
}
