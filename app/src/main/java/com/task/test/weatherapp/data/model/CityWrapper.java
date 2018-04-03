package com.task.test.weatherapp.data.model;

import java.util.ArrayList;
import java.util.List;

public class CityWrapper {

    public static List<CityWrapper> createCityWrapper(List<String> cityNames) {
        ArrayList<CityWrapper> list = new ArrayList<>();
        for (String cityName : cityNames) {
            list.add(new CityWrapper(cityName));
        }
        return list;
    }

    private String mCityName;
    private OWeatherPojo mWeatherPojo;

    public CityWrapper(String cityName) {
        mCityName = cityName;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public OWeatherPojo getWeatherPojo() {
        return mWeatherPojo;
    }

    public void setWeatherPojo(OWeatherPojo weatherPojo) {
        mWeatherPojo = weatherPojo;
    }
}
