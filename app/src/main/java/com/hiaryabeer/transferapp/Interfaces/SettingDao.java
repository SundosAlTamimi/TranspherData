package com.hiaryabeer.transferapp.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.hiaryabeer.transferapp.appSettings;

import java.util.List;

@Dao
public interface SettingDao {
    @Insert
    void insert(appSettings appSettings);
    @Update
    void update(appSettings appSettings);
    @Delete
    void delete(appSettings appSettings);


    @Query("Delete from SETTINGS_TABLE")
    void deleteALL();

    @Query ("select * from SETTINGS_TABLE")
    List<appSettings> getallsetting();

    @Query ("select IP_ADDRESS from SETTINGS_TABLE")
    String getIpAddress();
    @Query ("select COMPANYNO from SETTINGS_TABLE")
    String getCono();

    @Query ("update SETTINGS_TABLE set  YEARS =:coYear ")
    void  updateCompanyYear(String coYear);

    @Query ("update SETTINGS_TABLE set  COMPANYNO=:cono ")
    void  updateCompanyInfo(String cono);

    @Query ("select USERNO from SETTINGS_TABLE")
    String getUserNo();

}
