package com.task.test.weatherapp.di.module;


import com.task.test.weatherapp.data.service.ApiCommonService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ServiceModule {
    @Provides
    @Singleton
    ApiCommonService provideRetrofit(Retrofit retrofit) {
        return retrofit.create(ApiCommonService.class);
    }
}