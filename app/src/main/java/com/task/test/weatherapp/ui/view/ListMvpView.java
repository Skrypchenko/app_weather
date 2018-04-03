package com.task.test.weatherapp.ui.view;

import com.task.test.weatherapp.data.model.OWeatherPojo;

public interface ListMvpView extends MvpView {
    void updateItem(int id, OWeatherPojo pojo);
}
