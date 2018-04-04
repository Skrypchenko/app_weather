package com.task.test.weatherapp.ui.activity.main.parts;

import com.task.test.weatherapp.data.model.CityWrapper;

/**
 * Created by Yevgen on 04.04.2018.
 */

public class EventOpenCity {

    public CityWrapper mCityWrapper;

    public EventOpenCity(CityWrapper cityWrapper) {
        mCityWrapper = cityWrapper;
    }

    public CityWrapper getCityWrapper() {
        return mCityWrapper;
    }

    public void setCityWrapper(CityWrapper cityWrapper) {
        mCityWrapper = cityWrapper;
    }
}
