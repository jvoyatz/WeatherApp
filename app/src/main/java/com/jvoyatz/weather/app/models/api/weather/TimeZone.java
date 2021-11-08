
package com.jvoyatz.weather.app.models.api.weather;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TimeZone {

    @SerializedName("localtime")
    @Expose
    private String localtime;
    @SerializedName("utcOffset")
    @Expose
    private String utcOffset;
    @SerializedName("zone")
    @Expose
    private String zone;


    public TimeZone() {
    }


    public TimeZone(String localtime, String utcOffset, String zone) {
        super();
        this.localtime = localtime;
        this.utcOffset = utcOffset;
        this.zone = zone;
    }

    public String getLocaltime() {
        return localtime;
    }

    public void setLocaltime(String localtime) {
        this.localtime = localtime;
    }

    public String getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(String utcOffset) {
        this.utcOffset = utcOffset;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

}
