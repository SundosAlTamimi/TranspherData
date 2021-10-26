package com.hiaryabeer.transferapp.Interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.hiaryabeer.transferapp.Models.AllItems;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    public long[] insertAll(List<AllItems> allItems);

    @Query("SELECT * FROM ITEM_TABLE")
    List<AllItems>   getAll();

    @Query("DELETE FROM ITEM_TABLE")
   void  dELETEAll();
}
