package com.jvoyatz.weather.app.api;

import androidx.lifecycle.LiveData;

import com.jvoyatz.weather.app.models.api.CityResponse;
import com.jvoyatz.weather.app.models.api.weather.WeatherData;
import com.jvoyatz.weather.app.models.api.config.ApiResponse;
import com.jvoyatz.weather.app.models.api.weather.WeatherResponse;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface WorldWeatherAPI {

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET("weather.ashx")
    LiveData<ApiResponse<WeatherResponse>> getWeatherForecast(@Query("q") String query, @Query("num_of_days") int numOfDays, @Query("showlocaltime") String showLocalTimeValue, @Query("format")  String format);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET("search.ashx")
    LiveData<ApiResponse<CityResponse>> getCity(@Query("q") String query, @Query("timezone") String timezoneValue, @Query("format")  String format);
}
