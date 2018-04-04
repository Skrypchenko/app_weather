package com.task.test.weatherapp.ui.activity.main.parts;

public class EventDelItem {
    public int mId;
    public String mCityName;

    public EventDelItem(int id, String cityName) {
        mId = id;
        mCityName = cityName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }
}
