package com.jvoyatz.weather.app.repository;

import androidx.annotation.NonNull;

import com.jvoyatz.weather.app.api.WorldWeatherAPI;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
/**
 * Handles all the data needed to be fetched regarding
 * weather forecast.
 */
public class WeatherRepository {
    WorldWeatherAPI worldWeatherService;

    @Inject
    public WeatherRepository(WorldWeatherAPI worldWeatherService) {
        this.worldWeatherService = worldWeatherService;
    }

    /**
     * Fetches the weather forecast for the given value in the argument
     *
     * @param query the area for which, we want to fetch the current forecast
     */
    public void getCityWeatherForecast(@NonNull String query){

    }
}
