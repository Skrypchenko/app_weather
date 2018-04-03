package com.task.test.weatherapp.data.service;

import com.task.test.weatherapp.data.model.OpenWeatherPojo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiCommonService {
    @GET("/data/2.5/group")
    Observable<OpenWeatherPojo> loadWeatherForSeveralCities(
            @Query("id") String id,
            @Query("units") String units,
            @Query("appid") String appid,
            @Query("lang") String lang
    );
}
