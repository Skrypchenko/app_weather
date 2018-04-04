package com.task.test.weatherapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class WeatherDbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "cityweather.db";

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateDb(sqLiteDatabase, 0, 0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void updateDb(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("create table " + CityWrapperSchema.NAME + "(" +
                    " _id integer primary key autoincrement, " +
                    CityWrapperSchema.FIELDS.NAME + ", " +
                    CityWrapperSchema.FIELDS.LAST_UPDATE + ", " +
                    CityWrapperSchema.FIELDS.CURRENT_TEMP + ", " +
                    CityWrapperSchema.FIELDS.MAX_TEMP + ", " +
                    CityWrapperSchema.FIELDS.MIN_TEMP + ", " +
                    CityWrapperSchema.FIELDS.HUMIDITY + ", " +
                    CityWrapperSchema.FIELDS.PRESSURE + ", " +
                    CityWrapperSchema.FIELDS.WIND + ", " +
                    CityWrapperSchema.FIELDS.DESCR + ", " +
                    CityWrapperSchema.FIELDS.ICON + ")"
            );

            insertTestCities(db);
        }
    }

    private static void insertTestCities(SQLiteDatabase db) {
        ContentValues citiesValue = new ContentValues();
        citiesValue.put(CityWrapperSchema.FIELDS.NAME, "Kharkiv");
        db.insert(CityWrapperSchema.NAME, null, citiesValue);

        citiesValue = new ContentValues();
        citiesValue.put(CityWrapperSchema.FIELDS.NAME, "Kiev");
        db.insert(CityWrapperSchema.NAME, null, citiesValue);

        citiesValue = new ContentValues();
        citiesValue.put(CityWrapperSchema.FIELDS.NAME, "Dnipro");
        db.insert(CityWrapperSchema.NAME, null, citiesValue);

        citiesValue = new ContentValues();
        citiesValue.put(CityWrapperSchema.FIELDS.NAME, "London");
        db.insert(CityWrapperSchema.NAME, null, citiesValue);


    }
}
