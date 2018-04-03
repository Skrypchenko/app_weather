package com.task.test.weatherapp.ui.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.task.test.weatherapp.R;
import com.task.test.weatherapp.data.model.CityWrapper;
import com.task.test.weatherapp.data.model.OWeatherPojo;
import com.task.test.weatherapp.ui.activity.BaseFragment;

public class DetailFragment extends BaseFragment {

    public static DetailFragment newInstance(CityWrapper cityWrapper) {
        DetailFragment f = new DetailFragment();
        f.setCityWrapper(cityWrapper);
        return f;
    }

    private CityWrapper mCityWrapper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public CityWrapper getCityWrapper() {
        return mCityWrapper;
    }

    public void setCityWrapper(CityWrapper cityWrapper) {
        mCityWrapper = cityWrapper;
    }
}
