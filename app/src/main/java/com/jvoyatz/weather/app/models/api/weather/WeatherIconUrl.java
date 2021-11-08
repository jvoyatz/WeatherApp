
package com.jvoyatz.weather.app.models.api.weather;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class WeatherIconUrl {

    @SerializedName("value")
    @Expose
    private String value;


    public WeatherIconUrl() {
    }


    public WeatherIconUrl(String value) {
        super();
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
