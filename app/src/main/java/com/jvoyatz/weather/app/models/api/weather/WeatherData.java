
package com.jvoyatz.weather.app.models.api.weather;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jvoyatz.weather.app.models.api.weather.ClimateAverage;
import com.jvoyatz.weather.app.models.api.weather.CurrentCondition;
import com.jvoyatz.weather.app.models.api.weather.Request;
import com.jvoyatz.weather.app.models.api.weather.TimeZone;
import com.jvoyatz.weather.app.models.api.weather.Weather;


public class WeatherData {

    @SerializedName("request")
    @Expose
    private List<Request> request = null;
    @SerializedName("time_zone")
    @Expose
    private List<TimeZone> timeZone = null;
    @SerializedName("current_condition")
    @Expose
    private List<CurrentCondition> currentCondition = null;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("ClimateAverages")
    @Expose
    private List<ClimateAverage> climateAverages = null;


    public WeatherData() {
    }

    public WeatherData(List<Request> request, List<TimeZone> timeZone, List<CurrentCondition> currentCondition, List<Weather> weather, List<ClimateAverage> climateAverages) {
        super();
        this.request = request;
        this.timeZone = timeZone;
        this.currentCondition = currentCondition;
        this.weather = weather;
        this.climateAverages = climateAverages;
    }

    public List<Request> getRequest() {
        return request;
    }

    public void setRequest(List<Request> request) {
        this.request = request;
    }

    public List<TimeZone> getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(List<TimeZone> timeZone) {
        this.timeZone = timeZone;
    }

    public List<CurrentCondition> getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(List<CurrentCondition> currentCondition) {
        this.currentCondition = currentCondition;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public List<ClimateAverage> getClimateAverages() {
        return climateAverages;
    }

    public void setClimateAverages(List<ClimateAverage> climateAverages) {
        this.climateAverages = climateAverages;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "request=" + request +
                ", timeZone=" + timeZone +
                ", currentCondition=" + currentCondition +
                ", weather=" + weather +
                ", climateAverages=" + climateAverages +
                '}';
    }
}
