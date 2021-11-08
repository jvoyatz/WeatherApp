
package com.jvoyatz.weather.app.models.api.weather;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Hourly {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("tempC")
    @Expose
    private String tempC;
    @SerializedName("tempF")
    @Expose
    private String tempF;
    @SerializedName("windspeedMiles")
    @Expose
    private String windspeedMiles;
    @SerializedName("windspeedKmph")
    @Expose
    private String windspeedKmph;
    @SerializedName("winddirDegree")
    @Expose
    private String winddirDegree;
    @SerializedName("winddir16Point")
    @Expose
    private String winddir16Point;
    @SerializedName("weatherCode")
    @Expose
    private String weatherCode;
    @SerializedName("weatherIconUrl")
    @Expose
    private List<WeatherIconUrl> weatherIconUrl = null;
    @SerializedName("weatherDesc")
    @Expose
    private List<WeatherDesc> weatherDesc = null;
    @SerializedName("precipMM")
    @Expose
    private String precipMM;
    @SerializedName("precipInches")
    @Expose
    private String precipInches;
    @SerializedName("humidity")
    @Expose
    private String humidity;
    @SerializedName("visibility")
    @Expose
    private String visibility;
    @SerializedName("visibilityMiles")
    @Expose
    private String visibilityMiles;
    @SerializedName("pressure")
    @Expose
    private String pressure;
    @SerializedName("pressureInches")
    @Expose
    private String pressureInches;
    @SerializedName("cloudcover")
    @Expose
    private String cloudcover;
    @SerializedName("HeatIndexC")
    @Expose
    private String heatIndexC;
    @SerializedName("HeatIndexF")
    @Expose
    private String heatIndexF;
    @SerializedName("DewPointC")
    @Expose
    private String dewPointC;
    @SerializedName("DewPointF")
    @Expose
    private String dewPointF;
    @SerializedName("WindChillC")
    @Expose
    private String windChillC;
    @SerializedName("WindChillF")
    @Expose
    private String windChillF;
    @SerializedName("WindGustMiles")
    @Expose
    private String windGustMiles;
    @SerializedName("WindGustKmph")
    @Expose
    private String windGustKmph;
    @SerializedName("FeelsLikeC")
    @Expose
    private String feelsLikeC;
    @SerializedName("FeelsLikeF")
    @Expose
    private String feelsLikeF;
    @SerializedName("chanceofrain")
    @Expose
    private String chanceofrain;
    @SerializedName("chanceofremdry")
    @Expose
    private String chanceofremdry;
    @SerializedName("chanceofwindy")
    @Expose
    private String chanceofwindy;
    @SerializedName("chanceofovercast")
    @Expose
    private String chanceofovercast;
    @SerializedName("chanceofsunshine")
    @Expose
    private String chanceofsunshine;
    @SerializedName("chanceoffrost")
    @Expose
    private String chanceoffrost;
    @SerializedName("chanceofhightemp")
    @Expose
    private String chanceofhightemp;
    @SerializedName("chanceoffog")
    @Expose
    private String chanceoffog;
    @SerializedName("chanceofsnow")
    @Expose
    private String chanceofsnow;
    @SerializedName("chanceofthunder")
    @Expose
    private String chanceofthunder;
    @SerializedName("uvIndex")
    @Expose
    private String uvIndex;

    public Hourly() {
    }

    public Hourly(String time, String tempC, String tempF, String windspeedMiles, String windspeedKmph, String winddirDegree, String winddir16Point, String weatherCode, List<WeatherIconUrl> weatherIconUrl, List<WeatherDesc> weatherDesc, String precipMM, String precipInches, String humidity, String visibility, String visibilityMiles, String pressure, String pressureInches, String cloudcover, String heatIndexC, String heatIndexF, String dewPointC, String dewPointF, String windChillC, String windChillF, String windGustMiles, String windGustKmph, String feelsLikeC, String feelsLikeF, String chanceofrain, String chanceofremdry, String chanceofwindy, String chanceofovercast, String chanceofsunshine, String chanceoffrost, String chanceofhightemp, String chanceoffog, String chanceofsnow, String chanceofthunder, String uvIndex) {
        super();
        this.time = time;
        this.tempC = tempC;
        this.tempF = tempF;
        this.windspeedMiles = windspeedMiles;
        this.windspeedKmph = windspeedKmph;
        this.winddirDegree = winddirDegree;
        this.winddir16Point = winddir16Point;
        this.weatherCode = weatherCode;
        this.weatherIconUrl = weatherIconUrl;
        this.weatherDesc = weatherDesc;
        this.precipMM = precipMM;
        this.precipInches = precipInches;
        this.humidity = humidity;
        this.visibility = visibility;
        this.visibilityMiles = visibilityMiles;
        this.pressure = pressure;
        this.pressureInches = pressureInches;
        this.cloudcover = cloudcover;
        this.heatIndexC = heatIndexC;
        this.heatIndexF = heatIndexF;
        this.dewPointC = dewPointC;
        this.dewPointF = dewPointF;
        this.windChillC = windChillC;
        this.windChillF = windChillF;
        this.windGustMiles = windGustMiles;
        this.windGustKmph = windGustKmph;
        this.feelsLikeC = feelsLikeC;
        this.feelsLikeF = feelsLikeF;
        this.chanceofrain = chanceofrain;
        this.chanceofremdry = chanceofremdry;
        this.chanceofwindy = chanceofwindy;
        this.chanceofovercast = chanceofovercast;
        this.chanceofsunshine = chanceofsunshine;
        this.chanceoffrost = chanceoffrost;
        this.chanceofhightemp = chanceofhightemp;
        this.chanceoffog = chanceoffog;
        this.chanceofsnow = chanceofsnow;
        this.chanceofthunder = chanceofthunder;
        this.uvIndex = uvIndex;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public String getTempF() {
        return tempF;
    }

    public void setTempF(String tempF) {
        this.tempF = tempF;
    }

    public String getWindspeedMiles() {
        return windspeedMiles;
    }

    public void setWindspeedMiles(String windspeedMiles) {
        this.windspeedMiles = windspeedMiles;
    }

    public String getWindspeedKmph() {
        return windspeedKmph;
    }

    public void setWindspeedKmph(String windspeedKmph) {
        this.windspeedKmph = windspeedKmph;
    }

    public String getWinddirDegree() {
        return winddirDegree;
    }

    public void setWinddirDegree(String winddirDegree) {
        this.winddirDegree = winddirDegree;
    }

    public String getWinddir16Point() {
        return winddir16Point;
    }

    public void setWinddir16Point(String winddir16Point) {
        this.winddir16Point = winddir16Point;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public List<WeatherIconUrl> getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(List<WeatherIconUrl> weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public List<WeatherDesc> getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(List<WeatherDesc> weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public String getPrecipMM() {
        return precipMM;
    }

    public void setPrecipMM(String precipMM) {
        this.precipMM = precipMM;
    }

    public String getPrecipInches() {
        return precipInches;
    }

    public void setPrecipInches(String precipInches) {
        this.precipInches = precipInches;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getVisibilityMiles() {
        return visibilityMiles;
    }

    public void setVisibilityMiles(String visibilityMiles) {
        this.visibilityMiles = visibilityMiles;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getPressureInches() {
        return pressureInches;
    }

    public void setPressureInches(String pressureInches) {
        this.pressureInches = pressureInches;
    }

    public String getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(String cloudcover) {
        this.cloudcover = cloudcover;
    }

    public String getHeatIndexC() {
        return heatIndexC;
    }

    public void setHeatIndexC(String heatIndexC) {
        this.heatIndexC = heatIndexC;
    }

    public String getHeatIndexF() {
        return heatIndexF;
    }

    public void setHeatIndexF(String heatIndexF) {
        this.heatIndexF = heatIndexF;
    }

    public String getDewPointC() {
        return dewPointC;
    }

    public void setDewPointC(String dewPointC) {
        this.dewPointC = dewPointC;
    }

    public String getDewPointF() {
        return dewPointF;
    }

    public void setDewPointF(String dewPointF) {
        this.dewPointF = dewPointF;
    }

    public String getWindChillC() {
        return windChillC;
    }

    public void setWindChillC(String windChillC) {
        this.windChillC = windChillC;
    }

    public String getWindChillF() {
        return windChillF;
    }

    public void setWindChillF(String windChillF) {
        this.windChillF = windChillF;
    }

    public String getWindGustMiles() {
        return windGustMiles;
    }

    public void setWindGustMiles(String windGustMiles) {
        this.windGustMiles = windGustMiles;
    }

    public String getWindGustKmph() {
        return windGustKmph;
    }

    public void setWindGustKmph(String windGustKmph) {
        this.windGustKmph = windGustKmph;
    }

    public String getFeelsLikeC() {
        return feelsLikeC;
    }

    public void setFeelsLikeC(String feelsLikeC) {
        this.feelsLikeC = feelsLikeC;
    }

    public String getFeelsLikeF() {
        return feelsLikeF;
    }

    public void setFeelsLikeF(String feelsLikeF) {
        this.feelsLikeF = feelsLikeF;
    }

    public String getChanceofrain() {
        return chanceofrain;
    }

    public void setChanceofrain(String chanceofrain) {
        this.chanceofrain = chanceofrain;
    }

    public String getChanceofremdry() {
        return chanceofremdry;
    }

    public void setChanceofremdry(String chanceofremdry) {
        this.chanceofremdry = chanceofremdry;
    }

    public String getChanceofwindy() {
        return chanceofwindy;
    }

    public void setChanceofwindy(String chanceofwindy) {
        this.chanceofwindy = chanceofwindy;
    }

    public String getChanceofovercast() {
        return chanceofovercast;
    }

    public void setChanceofovercast(String chanceofovercast) {
        this.chanceofovercast = chanceofovercast;
    }

    public String getChanceofsunshine() {
        return chanceofsunshine;
    }

    public void setChanceofsunshine(String chanceofsunshine) {
        this.chanceofsunshine = chanceofsunshine;
    }

    public String getChanceoffrost() {
        return chanceoffrost;
    }

    public void setChanceoffrost(String chanceoffrost) {
        this.chanceoffrost = chanceoffrost;
    }

    public String getChanceofhightemp() {
        return chanceofhightemp;
    }

    public void setChanceofhightemp(String chanceofhightemp) {
        this.chanceofhightemp = chanceofhightemp;
    }

    public String getChanceoffog() {
        return chanceoffog;
    }

    public void setChanceoffog(String chanceoffog) {
        this.chanceoffog = chanceoffog;
    }

    public String getChanceofsnow() {
        return chanceofsnow;
    }

    public void setChanceofsnow(String chanceofsnow) {
        this.chanceofsnow = chanceofsnow;
    }

    public String getChanceofthunder() {
        return chanceofthunder;
    }

    public void setChanceofthunder(String chanceofthunder) {
        this.chanceofthunder = chanceofthunder;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }

}
