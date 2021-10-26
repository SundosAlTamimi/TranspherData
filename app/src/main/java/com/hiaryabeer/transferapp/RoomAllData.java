package com.hiaryabeer.transferapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hiaryabeer.transferapp.Interfaces.ItemDao;
import com.hiaryabeer.transferapp.Interfaces.ReplacementDao;
import com.hiaryabeer.transferapp.Interfaces.SettingDao;
import com.hiaryabeer.transferapp.Interfaces.StoreDao;
import com.hiaryabeer.transferapp.Interfaces.ZoneDao;
import com.hiaryabeer.transferapp.Models.AllItems;


@Database(entities =  {AllItems.class,ZoneModel.class, ReplacementModel.class, appSettings.class ,Store.class}, version = 7,exportSchema = false)
public abstract class RoomAllData extends RoomDatabase {
    private  static  RoomAllData database;
    public  static  String dataBaseName="DBRoomTransfer";
    public abstract ZoneDao zoneDao();

    public abstract ReplacementDao replacementDao();
    public abstract SettingDao settingDao();
    public abstract ItemDao itemDao();
    public abstract StoreDao storeDao();

    static final Migration MIGRATION_1_2 = new Migration(19, 21) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
//                    + "`name` TEXT, PRIMARY KEY(`id`))");
        }
    };

    public static synchronized  RoomAllData getInstanceDataBase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomAllData.class,dataBaseName)
            //        .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                     .fallbackToDestructiveMigration()
                    .build();
                   // .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        }
        return database;
    }



}








