package com.task.test.weatherapp.ui.presenter;


import android.util.Log;

import com.task.test.weatherapp.data.Settings;
import com.task.test.weatherapp.data.model.OWeatherPojo;
import com.task.test.weatherapp.data.service.ApiCommonService;
import com.task.test.weatherapp.di.ConfigPersistent;
import com.task.test.weatherapp.ui.view.ListMvpView;

import java.util.List;

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

    public void load(List<String> cities) {
        for (int i = 0; i < cities.size(); i++) {
            final int id = i;
            mApi.loadWeatherForCity(cities.get(i), "metric", mSettings.getApiKey(), "ru")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<OWeatherPojo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                                /*Lgi.err(e.getMessage(), e);
                                Lgi.toastLong(getActivity(), "error");*/
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


}
