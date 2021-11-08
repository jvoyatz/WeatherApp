package com.jvoyatz.weather.app.models.api.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("data")
    @Expose
    private WeatherData data;

    /**
     * No args constructor for use in serialization
     */
    public WeatherResponse() {
    }


    public WeatherData getData() {
        return data;
    }

    public void setData(WeatherData data) {
        this.data = data;
    }
}