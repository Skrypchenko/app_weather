package com.task.test.weatherapp.ui.presenter;


import android.util.Log;

import com.task.test.weatherapp.data.Settings;
import com.task.test.weatherapp.data.model.CityWrapper;
import com.task.test.weatherapp.data.model.OWeatherPojo;
import com.task.test.weatherapp.data.service.ApiCommonService;
import com.task.test.weatherapp.di.ConfigPersistent;
import com.task.test.weatherapp.ui.view.ListMvpView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


@ConfigPersistent
public class ListPresenter extends BasePresenter<ListMvpView> {

    private ApiCommonService mApi;
    private Settings mSettings;

    @Inject
    public ListPresenter(ApiCommonService api, Settings settings) {
        mApi = api;
        mSettings = settings;
    }

    private List<CityWrapper> mCities = new ArrayList<>();

    public void test() {
        mCities.add(new CityWrapper("Kharkiv"));
        mCities.add(new CityWrapper("Kiev"));
        mCities.add(new CityWrapper("Dnipro"));
        mCities.add(new CityWrapper("London"));
        load(mCities);
    }

    public void load(List<CityWrapper> cities) {
        for (int i = 0; i < cities.size(); i++) {
            final UUID id = cities.get(i).getUid();
            mApi.loadWeatherForCity(cities.get(i).getCityName(), "metric", mSettings.getApiKey(), "ru")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<OWeatherPojo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Log.d("fatal", e.getMessage());

                        }

                        @Override
                        public void onNext(OWeatherPojo oWeatherPojo) {
                            Log.v("fatal", "onNext");
                            getMvpView().updateItem(id, oWeatherPojo);
                        }
                    });
        }
    }

    public void load(final UUID id, CityWrapper wrapper) {
        mApi.loadWeatherForCity(wrapper.getCityName(), "metric", mSettings.getApiKey(), "ru")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OWeatherPojo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d("fatal", e.getMessage());

                    }

                    @Override
                    public void onNext(OWeatherPojo oWeatherPojo) {
                        getMvpView().updateItem(id, oWeatherPojo);
                    }
                });
    }


}
