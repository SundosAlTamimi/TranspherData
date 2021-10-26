package com.hiaryabeer.transferapp;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "REPLACEMENT_TABLE")
public  class ReplacementModel {

    @ColumnInfo(name = "UserNO")
    String UserNO;
    public String getUserNO() {
        return UserNO;
    }

    public void setUserNO(String userNO) {
        UserNO = userNO;
    }
    @ColumnInfo(name = "FROMSTORE")
    String From;
    @ColumnInfo(name = "TOSTORE")
    String To;
    @ColumnInfo(name = "ZONECODE")
    String Zone;
    @ColumnInfo(name = "ITEMCODE")
    String Itemcode;
    @ColumnInfo(name = "ISPOSTED")
    String IsPosted;
    @ColumnInfo(name = "REPLACEMENTDATE")
    String ReplacementDate;
    @ColumnInfo(name = "QTY")
    String Qty;
    @ColumnInfo(name = "ITEMNAME")
    String itemname;
    @ColumnInfo(name = "TransNumber")
    String transNumber;

    public String getTransNumber() {
        return transNumber;
    }

    public void setTransNumber(String transNumber) {
        this.transNumber = transNumber;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }
    @ColumnInfo(name = "DEVICEID")
    String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getRecQty() {
        return recQty;
    }

    public void setRecQty(String recQty) {
        this.recQty = recQty;
    }

    @ColumnInfo(name = "RECQTY")
    private   String recQty;
    @PrimaryKey(autoGenerate = true)
    int SERIALZONE;

    public ReplacementModel(String from, String to, String zone, String itemcode, String qty) {
        From = from;
        To = to;
        Zone = zone;
        Itemcode = itemcode;
        Qty = qty;
    }
    String ToName;

    String FromName;

    public String getToName() {
        return ToName;
    }

    public void setToName(String toName) {
        ToName = toName;
    }

    public String getFromName() {
        return FromName;
    }

    public void setFromName(String fromName) {
        FromName = fromName;
    }

    public String getIsPosted() {
        return IsPosted;
    }

    public void setIsPosted(String isPosted) {
        IsPosted = isPosted;
    }

    public String getReplacementDate() {
        return ReplacementDate;
    }

    public void setReplacementDate(String replacementDate) {
        ReplacementDate = replacementDate;
    }

    public int getSERIALZONE() {
        return SERIALZONE;
    }

    public void setSERIALZONE(int SERIALZONE) {
        this.SERIALZONE = SERIALZONE;
    }

    public ReplacementModel() {
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public String getItemcode() {
        return Itemcode;
    }

    public void setItemcode(String itemcode) {
        Itemcode = itemcode;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }
    public JSONObject getJSONObjectDelphi() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("FROMSTR", From);
            obj.put("TOSTR", To);
            obj.put("ZONE", Zone);
            obj.put("ITEMCODE", Itemcode);
            obj.put("QTY", recQty);
            obj.put("DEVICEID", deviceId);

        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
}

