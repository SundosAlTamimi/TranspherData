package com.hiaryabeer.transferapp;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "ZONETABLE")
public class ZoneModel {
    @PrimaryKey(autoGenerate = true)
    public int SERIALZONE;

    @ColumnInfo(name = "ZONECODE")
    private   String zoneCode;

    @ColumnInfo(name = "ITEMCODE")
    private   String itemCode;


    @ColumnInfo(name = "UserNO")
    String UserNO;
    public String getUserNO() {
        return UserNO;
    }

    public void setUserNO(String userNO) {
        UserNO = userNO;
    }

    @ColumnInfo(name = "QTYZONE")
    private   String qty;

    @ColumnInfo(name = "ISPOSTED")
    private   String isPostd;

    @ColumnInfo(name = "ZONEDATE")
    private   String zoneDate;

    @ColumnInfo(name = "ZONETIME")
    private   String zoneTime;

    @ColumnInfo(name = "STORENO")
    private   String storeNo;
    @ColumnInfo(name = "ZONENAME")
    private  String ZONENAME;

    @ColumnInfo(name = "ZONETYPE")
    private  String ZONETYPE;

    @ColumnInfo(name = "DEVICEID")
    String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    private String ItemName;



    public String getZONENAME() {
        return ZONENAME;
    }

    public void setZONENAME(String ZONENAME) {
        this.ZONENAME = ZONENAME;
    }

    public String getZONETYPE() {
        return ZONETYPE;
    }

    public void setZONETYPE(String ZONETYPE) {
        this.ZONETYPE = ZONETYPE;
    }

    public int getSERIALZONE() {
        return SERIALZONE;
    }

    public void setSERIALZONE(int SERIALZONE) {
        this.SERIALZONE = SERIALZONE;
    }

    public String getIsPostd() {
        return isPostd;
    }

    public void setIsPostd(String isPostd) {
        this.isPostd = isPostd;
    }

    public String getZoneDate() {
        return zoneDate;
    }

    public void setZoneDate(String zoneDate) {
        this.zoneDate = zoneDate;
    }

    public String getZoneTime() {
        return zoneTime;
    }

    public void setZoneTime(String zoneTime) {
        this.zoneTime = zoneTime;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public ZoneModel() {
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public JSONObject getJSONObjectDelphi() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("ZONENO", zoneCode);
            obj.put("ITEMCODE", itemCode);
            obj.put("QTY", qty);
            obj.put("TRDATE", zoneDate);
            obj.put("TRTIME", zoneTime);
            obj.put("DEVICEID", deviceId);

        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }


    String deletedflage;

    public String getDeletedflage() {
        return deletedflage;
    }

    public void setDeletedflage(String deletedflage) {
        this.deletedflage = deletedflage;
    }
}
