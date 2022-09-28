package com.ncode.devstresstask.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ncode.devstresstask.model.LocationModel;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String  dbName="Locations";
    private static String tbName="loc_data";


    public DataBaseHelper(@Nullable Context context) {
        super(context, dbName, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       String createTable="Create table "+tbName+"(id INTEGER primary key autoincrement,loc_name TEXT,loc_LAT_LANG TEXT,loc_address TEXT)";
       sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+tbName);
    }

    public void insertLocation(String name,String latlang,String address)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("loc_name",name);
        contentValues.put("loc_LAT_LANG",latlang);
        contentValues.put("loc_address",address);
        database.insert(tbName,null,contentValues);
    }

    @SuppressLint("Range")
    public ArrayList<LocationModel> getLocationList()
    {
        ArrayList<LocationModel> locationModels=new ArrayList<>();
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor=database.rawQuery("select * from "+tbName,null);
        while (cursor.moveToNext())
        {
            String id,name,latlang,address;
            id=String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
            name=cursor.getString(cursor.getColumnIndex("loc_name"));
            latlang=cursor.getString(cursor.getColumnIndex("loc_LAT_LANG"));
            address=cursor.getString(cursor.getColumnIndex("loc_address"));
            locationModels.add(new LocationModel(id,name,latlang,address));
        }

        return locationModels;
    }

    public void updateLocation(String id,String name,String latlang,String address)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("loc_name",name);
        contentValues.put("loc_LAT_LANG",latlang);
        contentValues.put("loc_address",address);
        database.update(tbName,contentValues,"id=?",new String[]{id});

    }

    public void deleteLocation(String id)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(tbName,"id=?",new String[] {id});
    }

}
