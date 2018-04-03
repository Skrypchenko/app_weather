package com.task.test.weatherapp.data.service;

import com.task.test.weatherapp.data.model.OWeatherPojo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiCommonService {
    @GET("/data/2.5/weather")
    Observable<OWeatherPojo> loadWeatherForCity(
            @Query("q") String city,
            @Query("units") String units,
            @Query("appid") String appid,
            @Query("lang") String lang
    );
}
