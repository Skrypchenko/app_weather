package com.task.test.weatherapp.ui.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.task.test.weatherapp.di.component.ActivityComponent;


public class BaseFragment extends Fragment {

    public ActivityComponent getComponent() {
        return ((BaseActivity) getActivity()).activityComponent();
    }
    

    public boolean isInetAvailable() {
        return ((BaseActivity) getActivity()).isOnline();
    }



}
