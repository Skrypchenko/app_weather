package com.task.test.weatherapp.ui.presenter;


import com.task.test.weatherapp.ui.view.MvpView;

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}