package com.task.test.weatherapp.ui.view;

import com.task.test.weatherapp.data.model.OWeatherPojo;

import java.util.UUID;

public interface ListMvpView extends MvpView {
    void updateItem(UUID id, OWeatherPojo pojo);
}
