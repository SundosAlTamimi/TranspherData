package com.hiaryabeer.transferapp.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ITEM_TABLE")
public  class AllItems {
    @PrimaryKey(autoGenerate = true)
    int SERIAL;

    public int getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(int SERIAL) {
        this.SERIAL = SERIAL;
    }

    @ColumnInfo(name = "ITEMNAME")
    String ItemName;
    @ColumnInfo(name = "ITEMOCODE")
    String ItemOcode;


    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemOcode() {
        return ItemOcode;
    }

    public void setItemOcode(String itemOcode) {
        ItemOcode = itemOcode;
    }
}
