package com.hiaryabeer.transferapp.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.hiaryabeer.transferapp.ZoneModel;

import java.util.List;

@Dao
public interface ZoneDao {
    @Insert
   public long[] insertAll(List<ZoneModel> zones);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ZoneModel zones);

    @Delete
    public void delete(ZoneModel item);


    @Update
    public  void update(ZoneModel item);

    @Query("DELETE FROM ZONETABLE")
    void deleteAll();

 @Query("UPDATE ZONETABLE SET QTYZONE = :qty, ZONECODE= :zone WHERE ITEMCODE= :barcode AND ISPOSTED='0'" )
 int updateQTY(String barcode, String qty, String zone);


    @Query("UPDATE ZONETABLE SET QTYZONE = :qty WHERE ZONECODE= :ZONEbarcode AND ITEMCODE= :barcode AND ISPOSTED='0'" )
    int reduceQTY(String barcode, String qty, String ZONEbarcode);
    @Query("UPDATE ZONETABLE SET QTYZONE = :qty WHERE ZONECODE= :ZONEbarcode AND ITEMCODE= :barcode AND ISPOSTED='0'" )
    int updateQTYreduced(String barcode, String qty, String ZONEbarcode);

    @Query("SELECT * FROM ZONETABLE ")
    public List<ZoneModel> getAllZones();
    @Query("SELECT * FROM ZONETABLE WHERE ISPOSTED='0'")
    public List<ZoneModel> getAllZonesUnposted();


    @Query("SELECT DISTINCT ZONECODE FROM ZONETABLE WHERE ISPOSTED='0'")
    public List<String> getZonesUnposted();


   /* @Query("SELECT ZONECODE FROM ZONETABLE WHERE ISPOSTED='0'")
    public List<ZoneModel> getZonesUnpostedObj();*/

    @Query("SELECT * FROM ZONETABLE where  SERIALZONE IN(:Itemid) ")
    public  List<ZoneModel> getZonebyId(int Itemid);

// @Query("update  ZONETABLE  set ISPOSTED =1 where  ZONECODE = zoneCode ")
// public  void updateZoneIsPosted(String zoneCode);


 @Query("SELECT * FROM ZONETABLE where ISPOSTED = :posted")
 public  List<ZoneModel> getUnpostedZone(String posted);

 @Query("SELECT * FROM ZONETABLE ")
 public  List<ZoneModel> getUnpostedZone2();

 @Query("UPDATE ZONETABLE SET ISPOSTED='1' WHERE ISPOSTED='0' ")
  public   void updateZonePosted();
    @Query("DELETE FROM ZONETABLE WHERE ITEMCODE= :barcode AND ISPOSTED='0'")
    public int  deletezone(String barcode);
    @Query("DELETE FROM ZONETABLE WHERE ZONECODE= :barcode AND ISPOSTED='0'")
    public int  deletezonedata(String barcode);

    @Query("SELECT * FROM ZONETABLE WHERE ZONECODE= :zonebare AND ITEMCODE= :itembarcode AND ISPOSTED='0'")
    public ZoneModel  getzone(String zonebare, String itembarcode);



    @Query("DELETE FROM ZONETABLE WHERE ITEMCODE= :barcode AND ISPOSTED='0'")
    public int  deleteitem(String barcode);

    @Query("SELECT SUM(QTYZONE) FROM ZONETABLE WHERE ZONECODE= :barcode AND ISPOSTED='0'")
    public int  GetQtyOfZone(String barcode);
    @Query("SELECT ZONENAME FROM ZONETABLE WHERE ZONECODE= :barcode")
   public String GetNameOfZone(String barcode);

    @Query("SELECT COUNT(*) FROM ZONETABLE WHERE ITEMCODE= :barcode AND ISPOSTED='0'")
    public int Exists(String barcode);

    @Query("SELECT DISTINCT ITEMCODE FROM ZONETABLE WHERE ISPOSTED='0'")
    public List <String>getallitems();

    @Query ("select SUM(QTYZONE) from ZONETABLE WHERE ITEMCODE= :itemcode AND ISPOSTED='0'")
    int getsumofqty(String itemcode);

    @Query ("select * from ZONETABLE WHERE ZONECODE= :zonecode AND ISPOSTED='0'")
    List<ZoneModel> getzoneRows(String zonecode);


    @Query("DELETE FROM ZONETABLE WHERE ZONECODE= :barcode AND ITEMCODE= :itemcode AND ISPOSTED='0'")
    public int  deleteITEM(String barcode, String itemcode);
    @Query("UPDATE ZONETABLE SET QTYZONE= :qty ,UserNO= :USERNUM, deletedflage='1' WHERE ZONECODE= :barcode AND ITEMCODE= :itemcode AND ISPOSTED='0'")
    public int UpdateItemStatus(String barcode, String qty, String itemcode, String USERNUM);



}
