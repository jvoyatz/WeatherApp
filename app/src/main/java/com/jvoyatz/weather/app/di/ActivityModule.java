package com.jvoyatz.weather.app.di;

import android.os.Handler;
import android.os.Looper;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public class ActivityModule {
    @Provides
    public static Handler provideHandler() {
        return new Handler(Looper.getMainLooper());
    }
}
