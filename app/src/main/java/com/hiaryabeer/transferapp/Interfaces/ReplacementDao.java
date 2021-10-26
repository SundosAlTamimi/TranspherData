package com.hiaryabeer.transferapp.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.hiaryabeer.transferapp.ReplacementModel;

import java.util.List;

@Dao
public interface ReplacementDao {
    @Insert
    public long[] insertAll(List<ReplacementModel> replacements);
    @Insert
    void insert(ReplacementModel replacementModel);
    @Update
    void update(ReplacementModel replacementModel);
    @Delete
    int delete(ReplacementModel replacementModel);


    @Query("Delete from REPLACEMENT_TABLE")
    void deleteALL();

    @Query ("select * from REPLACEMENT_TABLE WHERE ISPOSTED='0'")
    List<ReplacementModel>getallReplacement();
    @Query ("select * from REPLACEMENT_TABLE WHERE ISPOSTED='0'AND ZONECODE= :Zone")
    List<ReplacementModel>getzoneReplacement(String Zone);
    @Query("SELECT * FROM REPLACEMENT_TABLE where ISPOSTED = :s")
    List<ReplacementModel> getUnpostedReplacement(String s);


    @Query("UPDATE REPLACEMENT_TABLE SET  ISPOSTED='1' WHERE ISPOSTED='0' ")
    void updateReplashmentPosted();
    @Query("UPDATE REPLACEMENT_TABLE SET RECQTY = :qty WHERE ITEMCODE= :barcode AND ISPOSTED='0'" )
    int updateQTY(String barcode, String qty);

    @Query("DELETE FROM REPLACEMENT_TABLE WHERE ITEMCODE= :barcode AND FROMSTORE= :FrSt AND TOSTORE= :ToSt AND ISPOSTED='0'")
    int  deleteReplacement(String barcode, String FrSt, String ToSt);




    @Query("SELECT * FROM REPLACEMENT_TABLE WHERE ITEMCODE = :s AND ISPOSTED='0'")
    ReplacementModel getReplacement(String s);

    @Query("SELECT  Max(CAST(TransNumber AS INTEGER))  as MaxTrans FROM REPLACEMENT_TABLE ")
    String getMaxReplacementNo();

    @Query("Delete from REPLACEMENT_TABLE WHERE TOSTORE= :ToSt AND ZONECODE= :Zone ")
   int deletezone(String Zone, String ToSt);

    @Query("DELETE FROM REPLACEMENT_TABLE WHERE ITEMCODE= :itemcode AND ZONECODE= :zonecode AND TOSTORE= :ToSt AND ISPOSTED='0'")
    int  deleteDbReplacement(String zonecode, String itemcode, String ToSt);
   /* @Query("UPDATE REPLACEMENT_TABLE WHERE ZONECODE= :zonecode AND TOSTORE= :ToSt AND ISPOSTED='0'" )
    int updateDBQTY(String qty, String zonecode, String itemcode, String ToSt);*/


    @Query("UPDATE REPLACEMENT_TABLE SET TransNumber= :TransNumber WHERE ISPOSTED='0' AND FROMSTORE= :FrSt AND TOSTORE= :ToSt AND ITEMCODE= :barcode ")
    int   updateVochNum(String FrSt, String ToSt,String barcode, String TransNumber);


    @Query("SELECT  Min(CAST(TransNumber AS INTEGER))  as MinTrans FROM REPLACEMENT_TABLE WHERE ISPOSTED = '0' ")
    String getMinVocherNo();
    @Query("SELECT  Max(CAST(TransNumber AS INTEGER))  as MaxTrans FROM REPLACEMENT_TABLE WHERE ISPOSTED = '0' ")
    String getMaxVocherNo();

    @Query("SELECT * FROM REPLACEMENT_TABLE WHERE TransNumber= :s AND ISPOSTED='0'")
    List<ReplacementModel> getReplacements(String s);


    @Query("UPDATE REPLACEMENT_TABLE SET ISPOSTED= '1' WHERE TransNumber= :TransNumber ")
    int   updatepostState(String TransNumber);

}
