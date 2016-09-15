package com.sbhachu.weather.application;

import android.app.Application;

public class WeatherApplication extends Application {

    private static final String TAG = WeatherApplication.class.getSimpleName();

    private static WeatherApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        WeatherApplication.application = this;
    }

    public static WeatherApplication getApplication() {
        return application;
    }
}
