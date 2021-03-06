package com.jvoyatz.weather.app.di;

import android.content.Context;

import androidx.room.Room;

import com.jvoyatz.weather.app.models.converters.weather.WeatherTypeListConverter;
import com.jvoyatz.weather.app.storage.db.CityDao;
import com.jvoyatz.weather.app.storage.db.WeatherDao;
import com.jvoyatz.weather.app.storage.db.WeatherDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import timber.log.Timber;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule{

    @Singleton
    @Provides
    public static WeatherDatabase providesWeatherDB(@ApplicationContext Context context, WeatherTypeListConverter weatherTypeListConverter){
        Timber.d("providesWeatherDB: " + weatherTypeListConverter.gson);
        WeatherDatabase db =
                Room.databaseBuilder(context,WeatherDatabase.class, "weather-app-database")
                .addTypeConverter(weatherTypeListConverter)
                .build();
        return db;
    }

    @Singleton
    @Provides
    public static CityDao provideCityDAO(WeatherDatabase weatherDatabase){
        return weatherDatabase.cityDAO();
    }

    @Singleton
    @Provides
    public static WeatherDao provideWeatherDAO(WeatherDatabase weatherDatabase){
        return weatherDatabase.weatherDAO();
    }
}
