package com.task.test.weatherapp.ui.view;

import com.task.test.weatherapp.data.model.CityWrapper;
import com.task.test.weatherapp.data.model.OWeatherPojo;

import java.util.List;
import java.util.UUID;

public interface ListMvpView extends MvpView {
    void updateItem(String cityName, OWeatherPojo pojo);
    void updateItemOffline(CityWrapper cityWrapper);
    void initAdapterByList(List<CityWrapper> list);
    boolean isOnline();
}
