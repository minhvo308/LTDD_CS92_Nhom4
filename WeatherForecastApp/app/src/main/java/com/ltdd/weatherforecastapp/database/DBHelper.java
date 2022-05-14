package com.ltdd.weatherforecastapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ltdd.weatherforecastapp.model.Coord;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;

    public DBHelper(@Nullable Context context) {
        super(context, "CityFavorite.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table CitiesFavorite(name TEXT primary key, lon TEXT, lat TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists CitiesFavorite");
    }

//    public void test() {
//        database.execSQL("drop table CitiesFavorite");
//    }

    public Boolean saveCitydata(String name, String lon, String lat) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("lon", lon);
        contentValues.put("lat", lat);

        long result = DB.insert("CitiesFavorite", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

//    public ArrayList<City> getCityList(String name){
//        if(name.contains("\'"))
//            name = name.replace("\'", "\'\'" );
//        Cursor cursor = database.rawQuery("select name, country from CitiesFavorite where name match '%" + name + "%'", new String[]{});
//        ArrayList<City> ct = new ArrayList<>();
//        while(cursor.moveToNext()) {
//            City city = new City(cursor.getString(0), cursor.getString(1));
//            ct.add(city);
//        }
//        return ct;
//    }

    //Lấy tọa độ theo tên thành phố
    public Coord getCoordByCityName(String name){
        SQLiteDatabase DB = this.getWritableDatabase();
        if(name.contains("\'"))
            name = name.replace("\'", "\'\'" );
        Cursor cursor = DB.rawQuery("select lon, lat from CitiesFavorite where name=?", new String[]{name});
        Coord coord = new Coord();
        while(cursor.moveToNext())
            coord = new Coord(cursor.getDouble(0), cursor.getDouble(1));
        return coord;
    }

    public Boolean deleteCitydata(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * From CitiesFavorite Where name=?", new String[]{name});
        if (cursor.getCount()>0) {
            long result = DB.delete("CitiesFavorite", "name=?", new String[]{name});
            if (result == -1)
                return false;
            else
                return true;
        }
        else
            return false;
    }

    public Cursor getListCity() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM CitiesFavorite", null);
        return cursor;
    }

//    public Coord getCoordByCityName(String name){
//        if(name.contains("\'"))
//            name = name.replace("\'", "\'\'" );
//        Cursor cursor = database.rawQuery("select [coord.lon], [coord.lat] from city where name match '" + name + "'", null);
//        Coord coord = new Coord();
//        while(cursor.moveToNext())
//            coord = new Coord(cursor.getDouble(0), cursor.getDouble(1));
//        return coord;
//    }
}
