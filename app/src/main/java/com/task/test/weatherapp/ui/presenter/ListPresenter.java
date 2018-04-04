package com.task.test.weatherapp.ui.presenter;


import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.task.test.weatherapp.data.Settings;
import com.task.test.weatherapp.data.model.CityWrapper;
import com.task.test.weatherapp.data.model.OWeatherPojo;
import com.task.test.weatherapp.data.service.ApiCommonService;
import com.task.test.weatherapp.db.CityWrapperDbController;
import com.task.test.weatherapp.di.ApplicationContext;
import com.task.test.weatherapp.di.ConfigPersistent;
import com.task.test.weatherapp.ui.view.ListMvpView;
import com.task.test.weatherapp.util.Lgi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


@ConfigPersistent
public class ListPresenter extends BasePresenter<ListMvpView> {

    private ApiCommonService mApi;
    private Settings mSettings;

    private CityWrapperDbController mDbController;

    private Context mContext;

    @Inject
    public ListPresenter(ApiCommonService api, Settings settings, @ApplicationContext Context context) {
        mApi = api;
        mSettings = settings;

        mContext = context;


    }


    public void init() {

        CityWrapperDbController.getInstance(mContext)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CityWrapperDbController>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Lgi.err(e.getMessage(), e);
                    }

                    @Override
                    public void onNext(CityWrapperDbController cityWrapperDbController) {
                        mDbController = cityWrapperDbController;
                        mDbController.getCities()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<List<CityWrapper>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.v("fatal", e.getMessage());
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onNext(List<CityWrapper> cityWrappers) {
                                        Lgi.p("onNext: cityWrappers.size(): " + cityWrappers.size());
                                        if (getMvpView().isOnline()) {
                                            load(cityWrappers);
                                        } else {
                                            getMvpView().initAdapterByList(cityWrappers);
                                        }
                                    }
                                });
                    }
                });
    }

    public void addNewCity(final String cityName) {
        mDbController.getCityWrapperByName(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CityWrapper>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CityWrapper cityWrapper) {
                        if (cityWrapper == null) {
                            final CityWrapper cityWrapper1 = new CityWrapper(cityName);
                            mDbController.justInsertCityWrapper(cityWrapper1)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<CityWrapper>() {
                                        @Override
                                        public void call(CityWrapper cityWrapper) {
                                            if (getMvpView().isOnline()) {
                                                load(cityWrapper.getCityName());
                                            } else {
                                                getMvpView().updateItemOffline(cityWrapper1);
                                            }
                                        }
                                    });
                        } else {
                            // future: if we need to update
                            /*mDbController.updateCityWrapper(cityWrapper)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<CityWrapper>() {
                                        @Override
                                        public void call(CityWrapper cityWrapper) {

                                        }
                                    });*/

                        }
                    }
                });
    }

    public void removeItem(String cityName) {
        mDbController.removeCityWrapperByName(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CityWrapper>() {
                    @Override
                    public void call(CityWrapper cityWrapper) {

                    }
                });
    }


    public void load(List<CityWrapper> cities) {
        for (final CityWrapper cityWrapper : cities) {
            final String cityName = cityWrapper.getCityName();
            mApi.loadWeatherForCity(cityName, "metric", mSettings.getApiKey(), "ru")
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
                            getMvpView().updateItem(cityName, oWeatherPojo);
                            mDbController.updateCityWrapper(cityWrapper)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<CityWrapper>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onNext(CityWrapper cityWrapper) {

                                        }
                                    });
                        }
                    });
        }
    }

    public void load(final String cityName) {
        mApi.loadWeatherForCity(cityName, "metric", mSettings.getApiKey(), "ru")
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
                    public void onNext(final OWeatherPojo oWeatherPojo) {
                        mDbController.getCityWrapperByName(cityName)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<CityWrapper>() {
                                    @Override
                                    public void call(CityWrapper cityWrapper) {
                                        if (cityWrapper != null) {
                                            cityWrapper.setWeatherPojo(oWeatherPojo);
                                            getMvpView().updateItem(cityName, oWeatherPojo);
                                            mDbController.updateCityWrapper(cityWrapper)
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Action1<CityWrapper>() {
                                                        @Override
                                                        public void call(CityWrapper cityWrapper) {

                                                        }
                                                    });
                                        }
                                    }
                                });


                    }
                });
    }

}
