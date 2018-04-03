package com.task.test.weatherapp.di.module;

import android.app.Application;

import com.task.test.weatherapp.data.Settings;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {
    @Provides
    @Singleton
    public Settings provideSettings(Application context){
        return new Settings(context);
    }
}