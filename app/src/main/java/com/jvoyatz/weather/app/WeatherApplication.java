package com.jvoyatz.weather.app;

import android.app.Application;

import com.jvoyatz.weather.app.BuildConfig;

import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;

/**
 * Read https://developer.android.com/training/dependency-injection/hilt-android#application-class
 *
 * @HiltAndroidApp starts the generation of code needed by Hilt as well as a dependency
 * container of application-level scope. This is the parent component of the app.
 */
@HiltAndroidApp
public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
