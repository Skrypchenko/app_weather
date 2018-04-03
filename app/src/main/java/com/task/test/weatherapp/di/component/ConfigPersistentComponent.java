package com.task.test.weatherapp.di.component;

import com.task.test.weatherapp.di.ConfigPersistent;
import com.task.test.weatherapp.di.module.ActivityModule;

import dagger.Component;

@ConfigPersistent
@Component(dependencies = AppComponent.class)
public interface ConfigPersistentComponent {
    ActivityComponent activityComponent(ActivityModule activityModule);
}