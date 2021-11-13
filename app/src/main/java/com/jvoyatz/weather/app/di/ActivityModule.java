package com.jvoyatz.weather.app.di;

import android.content.Context;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;

import com.jvoyatz.weather.app.storage.WeatherSearchesSuggestionsProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@InstallIn(ActivityComponent.class)
@Module
public class ActivityModule {

    @Provides
    public static SearchRecentSuggestions provideSearchRecentSuggestions(@ApplicationContext Context context){
        return new SearchRecentSuggestions(context, WeatherSearchesSuggestionsProvider.AUTHORITY, WeatherSearchesSuggestionsProvider.MODE);
    }

    @Provides
    public static Handler provideHandler(){
        return new Handler();
    }
}
