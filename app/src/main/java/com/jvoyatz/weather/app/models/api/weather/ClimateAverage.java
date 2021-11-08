
package com.jvoyatz.weather.app.models.api.weather;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ClimateAverage {

    @SerializedName("month")
    @Expose
    private List<Month> month = null;

    public ClimateAverage() {
    }


    public ClimateAverage(List<Month> month) {
        super();
        this.month = month;
    }

    public List<Month> getMonth() {
        return month;
    }

    public void setMonth(List<Month> month) {
        this.month = month;
    }

}
