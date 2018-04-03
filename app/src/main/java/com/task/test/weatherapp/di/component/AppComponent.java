package com.task.test.weatherapp.di.component;

import android.content.Context;


import com.task.test.weatherapp.data.Settings;
import com.task.test.weatherapp.data.service.ApiCommonService;
import com.task.test.weatherapp.di.ApplicationContext;
import com.task.test.weatherapp.di.module.ApplicationModule;
import com.task.test.weatherapp.di.module.NetworkModule;
import com.task.test.weatherapp.di.module.ServiceModule;
import com.task.test.weatherapp.di.module.SettingsModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;


@Singleton
@Component(
    modules = {
        ApplicationModule.class,
        SettingsModule.class,
        NetworkModule.class,
        ServiceModule.class
    }
)
public interface AppComponent {

    @ApplicationContext
    Context context();
    OkHttpClient getHttpClient();
    Settings getSettings();
    ApiCommonService getApiService();
}
