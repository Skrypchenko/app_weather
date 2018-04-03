package com.task.test.weatherapp.di.component;



import com.task.test.weatherapp.di.PerActivity;
import com.task.test.weatherapp.di.module.ActivityModule;
import com.task.test.weatherapp.ui.activity.main.ListFragment;
import com.task.test.weatherapp.ui.activity.main.MainActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(ListFragment fragment);

}