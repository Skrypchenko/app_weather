package com.task.test.weatherapp.ui.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.task.test.weatherapp.R;
import com.task.test.weatherapp.WeatherApp;
import com.task.test.weatherapp.di.component.ActivityComponent;
import com.task.test.weatherapp.di.component.ConfigPersistentComponent;
import com.task.test.weatherapp.di.component.DaggerConfigPersistentComponent;
import com.task.test.weatherapp.di.module.ActivityModule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public class BaseActivity extends AppCompatActivity {


    private static final String TAG = "WeatherApp.Activity";
    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final Map<Long, ConfigPersistentComponent> sComponentsMap = new HashMap<>();

    private ActivityComponent mActivityComponent;
    private long mActivityId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (!sComponentsMap.containsKey(mActivityId)) {
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .appComponent(WeatherApp.get(this).getComponent())
                    .build();
            sComponentsMap.put(mActivityId, configPersistentComponent);
        } else {
            configPersistentComponent = sComponentsMap.get(mActivityId);
        }
        mActivityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            sComponentsMap.remove(mActivityId);
        }
        super.onDestroy();
    }

    public ActivityComponent activityComponent() {
        return mActivityComponent;
    }



    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private int getFragmentContainer() {
        return R.id.base_fragment_container;
    }

    protected Fragment addFragment(Fragment fragment, String fragmentName){
        getSupportFragmentManager()
                .beginTransaction()
                .add(getFragmentContainer(), fragment, fragmentName)
                .addToBackStack(null)
                .commitAllowingStateLoss();
        return fragment;
    }

    protected Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(getFragmentContainer());
    }


    protected void replaceFragment(Fragment fragment,String fragmentName){
        removeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(getFragmentContainer(), fragment, fragmentName)
                .commitAllowingStateLoss();
    }

    protected void replaceFragment(Fragment fragment) {
        removeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(getFragmentContainer(), fragment, null)
                .commitAllowingStateLoss();
    }

    protected void replaceFragmentWithoutRemove(Fragment fragment,String fragmentName){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(getFragmentContainer(), fragment, fragmentName)
                .commitAllowingStateLoss();
    }

    protected void replaceFragmentWithoutRemove(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(getFragmentContainer(), fragment, null)
                .commitAllowingStateLoss();
    }



    protected void removeAddFragment(int container, Fragment fragment, String fragmentName){
        removeFragment(fragmentName);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(container, fragment, fragmentName);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    protected void removeFragment(String fragmentName){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fr = getSupportFragmentManager().findFragmentByTag(fragmentName);
        if(fr != null){
            ft.remove(fr);
            ft.commitAllowingStateLoss();
        }
    }

    protected void removeFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment f = getSupportFragmentManager().findFragmentById(getFragmentContainer());
        if (f != null) {
            ft.remove(f);
            ft.commitAllowingStateLoss();
        }
    }

}
