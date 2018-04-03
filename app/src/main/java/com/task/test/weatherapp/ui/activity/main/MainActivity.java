package com.task.test.weatherapp.ui.activity.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.task.test.weatherapp.R;
import com.task.test.weatherapp.ui.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Fragment f = ListFragment.newInstance();
        replaceFragment(f, ListFragment.class.getName());
    }


}
