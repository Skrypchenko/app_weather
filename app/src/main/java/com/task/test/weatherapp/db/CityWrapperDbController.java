package com.task.test.weatherapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telecom.Call;

import com.task.test.weatherapp.data.model.CityWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CityWrapperDbController {
    private static CityWrapperDbController mController;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static Observable<CityWrapperDbController> getInstance(final Context context) {

        Callable<CityWrapperDbController> callable = new Callable<CityWrapperDbController>() {
            @Override
            public CityWrapperDbController call() throws Exception {
                if (mController == null) {
                    mController = new CityWrapperDbController(context);
                }
                return mController;
            }
        };


        return makeObservable(callable)
                .subscribeOn(Schedulers.computation());
    }

    private CityWrapperDbController(final Context context) {
        mContext = context;
        mDatabase = new WeatherDbHelper(context).getWritableDatabase();
    }


    /*public List<CityWrapper> getCities() {
        List<CityWrapper> cities = new ArrayList<>();
        CityCursorWrapper cursor = queryCities(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cities.add(cursor.getCityWrapper());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return cities;
    }*/

    public Observable<List<CityWrapper>> getCities() {

        Callable<List<CityWrapper>> callable = new Callable<List<CityWrapper>>() {
            @Override
            public List<CityWrapper> call() throws Exception {
                List<CityWrapper> cities = new ArrayList<>();
                CityCursorWrapper cursor = queryCities(null, null);

                try {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        cities.add(cursor.getCityWrapper());
                        cursor.moveToNext();
                    }
                } finally {
                    cursor.close();
                }
                return cities;
            }
        };

        return makeObservable(callable)
                .subscribeOn(Schedulers.computation());
    }

    public Observable<CityWrapper> getCityWrapperByName(final String name) {

        Callable<CityWrapper> callable = new Callable<CityWrapper>() {
            @Override
            public CityWrapper call() throws Exception {
                CityCursorWrapper cursor = queryCities(
                        CityWrapperSchema.FIELDS.NAME + " = ?",
                        new String[]{name}
                );

                try {
                    if (cursor.getCount() == 0) {
                        return null;
                    }
                    cursor.moveToFirst();
                    return cursor.getCityWrapper();
                } finally {
                    cursor.close();
                }
            }
        };

        return makeObservable(callable)
                .subscribeOn(Schedulers.computation());
    }


    public Observable<CityWrapper> updateCityWrapper(final CityWrapper cityWrapper) {
        Callable<CityWrapper> callable = new Callable<CityWrapper>() {
            @Override
            public CityWrapper call() throws Exception {
                String name = cityWrapper.getCityName();
                ContentValues cv = getContentValues(cityWrapper);
                mDatabase.update(
                        CityWrapperSchema.NAME,
                        cv,
                        CityWrapperSchema.FIELDS.NAME + " = ?",
                        new String[]{name}
                );
                return cityWrapper;
            }
        };
        return makeObservable(callable)
                .subscribeOn(Schedulers.computation());
    }

    public Observable<CityWrapper> justInsertCityWrapper(final CityWrapper cityWrapper) {
        final ContentValues cv = getContentValues(cityWrapper);
        Callable<CityWrapper> callable = new Callable<CityWrapper>() {
            @Override
            public CityWrapper call() throws Exception {
                mDatabase.insert(CityWrapperSchema.NAME, null, cv);
                return cityWrapper;
            }
        };
        return makeObservable(callable)
                .subscribeOn(Schedulers.computation());
    }

    public Observable<CityWrapper> removeCityWrapperByName(final String name) {
        Callable<CityWrapper> callable = new Callable<CityWrapper>() {
            @Override
            public CityWrapper call() throws Exception {
                mDatabase.delete(
                        CityWrapperSchema.NAME,
                        CityWrapperSchema.FIELDS.NAME + " = ?",
                        new String[]{name}
                );
                return null;
            }
        };
        return makeObservable(callable)
                .subscribeOn(Schedulers.computation());
    }

    private static ContentValues getContentValues(CityWrapper cityWrapper) {
        ContentValues cv = new ContentValues();

        cv.put(CityWrapperSchema.FIELDS.NAME, cityWrapper.getCityName());
        cv.put(CityWrapperSchema.FIELDS.LAST_UPDATE, cityWrapper.getLastUpdate());
        cv.put(CityWrapperSchema.FIELDS.CURRENT_TEMP, cityWrapper.getCurrentTemp());
        cv.put(CityWrapperSchema.FIELDS.MAX_TEMP, cityWrapper.getMaxTemp());
        cv.put(CityWrapperSchema.FIELDS.MIN_TEMP, cityWrapper.getMinTemp());
        cv.put(CityWrapperSchema.FIELDS.HUMIDITY, cityWrapper.getHumidity());
        cv.put(CityWrapperSchema.FIELDS.PRESSURE, cityWrapper.getPressure());
        cv.put(CityWrapperSchema.FIELDS.WIND, cityWrapper.getWind());
        cv.put(CityWrapperSchema.FIELDS.DESCR, cityWrapper.getWeatherDescription());
        cv.put(CityWrapperSchema.FIELDS.ICON, cityWrapper.getIconName());

        return cv;
    }


    private CityCursorWrapper queryCities(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CityWrapperSchema.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CityCursorWrapper(cursor);
    }


    private static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            subscriber.onNext(func.call());
                        } catch(Exception ex) {
                            subscriber.onError(ex);
                        }
                    }
                });
    }


}
