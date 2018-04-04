package com.task.test.weatherapp;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.task.test.weatherapp.db.WeatherDbHelper;
import com.task.test.weatherapp.di.component.AppComponent;
import com.task.test.weatherapp.di.component.DaggerAppComponent;
import com.task.test.weatherapp.di.module.ApplicationModule;

public class WeatherApp extends MultiDexApplication {
    AppComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        WeatherDbHelper dbHelper = new WeatherDbHelper(getApplicationContext());

        mApplicationComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public AppComponent getComponent() {
        return mApplicationComponent;
    }

    public static WeatherApp get(Context context) {
        return (WeatherApp) context.getApplicationContext();
    }
}
