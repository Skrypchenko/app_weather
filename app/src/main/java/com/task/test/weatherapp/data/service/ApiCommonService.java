package com.task.test.weatherapp.data.service;

import com.task.test.weatherapp.data.model.OWeatherPojo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiCommonService {
    @GET("/data/2.5/weather")
    Observable<OWeatherPojo> loadWeatherForLatLon (
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("units") String units,
            @Query("appid") String appid,
            @Query("lang") String lang
    );
}
