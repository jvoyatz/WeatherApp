package com.jvoyatz.weather.app.models.entities.weather;

import javax.annotation.Nonnull;

public class WeatherDayHourEntity {
    private String weatherCode;
    private String chanceofhightemp;
    private String feelsLikeF;
    private String cloudcover;
    private String windChillC;
    private String windspeedMiles;
    private String winddirDegree;
    private String dewPointC;
    private String chanceofthunder;
    private String chanceoffrost;
    private String dewPointF;
    private String humidity;
    private String winddir16Point;
    private String windChillF;
    private String weatherIconUrl;
    private String tempF;
    private String precipMM;
    private String weatherDesc;
    private String chanceofrain;
    private String chanceofovercast;
    private String visibility;
    private String pressure;
    private String chanceofsunshine;
    private String windGustMiles;
    private String chanceofsnow;
    private String feelsLikeC;
    private String windspeedKmph;
    private String windGustKmph;
    private String chanceoffog;
    private String visibilityMiles;
    private String heatIndexC;
    private String time;
    private String precipInches;
    private String chanceofwindy;
    private String uvIndex;
    private String tempC;
    private String pressureInches;
    private String heatIndexF;
    private String chanceofremdry;

    public WeatherDayHourEntity() {
    }

    private WeatherDayHourEntity(Builder builder) {
        setWeatherCode(builder.weatherCode);
        setChanceofhightemp(builder.chanceofhightemp);
        setFeelsLikeF(builder.feelsLikeF);
        setCloudcover(builder.cloudcover);
        setWindChillC(builder.windChillC);
        setWindspeedMiles(builder.windspeedMiles);
        setWinddirDegree(builder.winddirDegree);
        setDewPointC(builder.dewPointC);
        setChanceofthunder(builder.chanceofthunder);
        setChanceoffrost(builder.chanceoffrost);
        setDewPointF(builder.dewPointF);
        setHumidity(builder.humidity);
        setWinddir16Point(builder.winddir16Point);
        setWindChillF(builder.windChillF);
        setWeatherIconUrl(builder.weatherIconUrl);
        setTempF(builder.tempF);
        setPrecipMM(builder.precipMM);
        setWeatherDesc(builder.weatherDesc);
        setChanceofrain(builder.chanceofrain);
        setChanceofovercast(builder.chanceofovercast);
        setVisibility(builder.visibility);
        setPressure(builder.pressure);
        setChanceofsunshine(builder.chanceofsunshine);
        setWindGustMiles(builder.windGustMiles);
        setChanceofsnow(builder.chanceofsnow);
        setFeelsLikeC(builder.feelsLikeC);
        setWindspeedKmph(builder.windspeedKmph);
        setWindGustKmph(builder.windGustKmph);
        setChanceoffog(builder.chanceoffog);
        setVisibilityMiles(builder.visibilityMiles);
        setHeatIndexC(builder.heatIndexC);
        setTime(builder.time);
        setPrecipInches(builder.precipInches);
        setChanceofwindy(builder.chanceofwindy);
        setUvIndex(builder.uvIndex);
        setTempC(builder.tempC);
        setPressureInches(builder.pressureInches);
        setHeatIndexF(builder.heatIndexF);
        setChanceofremdry(builder.chanceofremdry);
    }


    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getChanceofhightemp() {
        return chanceofhightemp;
    }

    public void setChanceofhightemp(String chanceofhightemp) {
        this.chanceofhightemp = chanceofhightemp;
    }

    public String getFeelsLikeF() {
        return feelsLikeF;
    }

    public void setFeelsLikeF(String feelsLikeF) {
        this.feelsLikeF = feelsLikeF;
    }

    public String getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(String cloudcover) {
        this.cloudcover = cloudcover;
    }

    public String getWindChillC() {
        return windChillC;
    }

    public void setWindChillC(String windChillC) {
        this.windChillC = windChillC;
    }

    public String getWindspeedMiles() {
        return windspeedMiles;
    }

    public void setWindspeedMiles(String windspeedMiles) {
        this.windspeedMiles = windspeedMiles;
    }

    public String getWinddirDegree() {
        return winddirDegree;
    }

    public void setWinddirDegree(String winddirDegree) {
        this.winddirDegree = winddirDegree;
    }

    public String getDewPointC() {
        return dewPointC;
    }

    public void setDewPointC(String dewPointC) {
        this.dewPointC = dewPointC;
    }

    public String getChanceofthunder() {
        return chanceofthunder;
    }

    public void setChanceofthunder(String chanceofthunder) {
        this.chanceofthunder = chanceofthunder;
    }

    public String getChanceoffrost() {
        return chanceoffrost;
    }

    public void setChanceoffrost(String chanceoffrost) {
        this.chanceoffrost = chanceoffrost;
    }

    public String getDewPointF() {
        return dewPointF;
    }

    public void setDewPointF(String dewPointF) {
        this.dewPointF = dewPointF;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWinddir16Point() {
        return winddir16Point;
    }

    public void setWinddir16Point(String winddir16Point) {
        this.winddir16Point = winddir16Point;
    }

    public String getWindChillF() {
        return windChillF;
    }

    public void setWindChillF(String windChillF) {
        this.windChillF = windChillF;
    }

    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(String weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public String getTempF() {
        return tempF;
    }

    public void setTempF(String tempF) {
        this.tempF = tempF;
    }

    public String getPrecipMM() {
        return precipMM;
    }

    public void setPrecipMM(String precipMM) {
        this.precipMM = precipMM;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public String getChanceofrain() {
        return chanceofrain;
    }

    public void setChanceofrain(String chanceofrain) {
        this.chanceofrain = chanceofrain;
    }

    public String getChanceofovercast() {
        return chanceofovercast;
    }

    public void setChanceofovercast(String chanceofovercast) {
        this.chanceofovercast = chanceofovercast;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getChanceofsunshine() {
        return chanceofsunshine;
    }

    public void setChanceofsunshine(String chanceofsunshine) {
        this.chanceofsunshine = chanceofsunshine;
    }

    public String getWindGustMiles() {
        return windGustMiles;
    }

    public void setWindGustMiles(String windGustMiles) {
        this.windGustMiles = windGustMiles;
    }

    public String getChanceofsnow() {
        return chanceofsnow;
    }

    public void setChanceofsnow(String chanceofsnow) {
        this.chanceofsnow = chanceofsnow;
    }

    public String getFeelsLikeC() {
        return feelsLikeC;
    }

    public void setFeelsLikeC(String feelsLikeC) {
        this.feelsLikeC = feelsLikeC;
    }

    public String getWindspeedKmph() {
        return windspeedKmph;
    }

    public void setWindspeedKmph(String windspeedKmph) {
        this.windspeedKmph = windspeedKmph;
    }

    public String getWindGustKmph() {
        return windGustKmph;
    }

    public void setWindGustKmph(String windGustKmph) {
        this.windGustKmph = windGustKmph;
    }

    public String getChanceoffog() {
        return chanceoffog;
    }

    public void setChanceoffog(String chanceoffog) {
        this.chanceoffog = chanceoffog;
    }

    public String getVisibilityMiles() {
        return visibilityMiles;
    }

    public void setVisibilityMiles(String visibilityMiles) {
        this.visibilityMiles = visibilityMiles;
    }

    public String getHeatIndexC() {
        return heatIndexC;
    }

    public void setHeatIndexC(String heatIndexC) {
        this.heatIndexC = heatIndexC;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrecipInches() {
        return precipInches;
    }

    public void setPrecipInches(String precipInches) {
        this.precipInches = precipInches;
    }

    public String getChanceofwindy() {
        return chanceofwindy;
    }

    public void setChanceofwindy(String chanceofwindy) {
        this.chanceofwindy = chanceofwindy;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public String getPressureInches() {
        return pressureInches;
    }

    public void setPressureInches(String pressureInches) {
        this.pressureInches = pressureInches;
    }

    public String getHeatIndexF() {
        return heatIndexF;
    }

    public void setHeatIndexF(String heatIndexF) {
        this.heatIndexF = heatIndexF;
    }

    public String getChanceofremdry() {
        return chanceofremdry;
    }

    public void setChanceofremdry(String chanceofremdry) {
        this.chanceofremdry = chanceofremdry;
    }


    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private String weatherCode;
        private String chanceofhightemp;
        private String feelsLikeF;
        private String cloudcover;
        private String windChillC;
        private String windspeedMiles;
        private String winddirDegree;
        private String dewPointC;
        private String chanceofthunder;
        private String chanceoffrost;
        private String dewPointF;
        private String humidity;
        private String winddir16Point;
        private String windChillF;
        private String weatherIconUrl;
        private String tempF;
        private String precipMM;
        private String weatherDesc;
        private String chanceofrain;
        private String chanceofovercast;
        private String visibility;
        private String pressure;
        private String chanceofsunshine;
        private String windGustMiles;
        private String chanceofsnow;
        private String feelsLikeC;
        private String windspeedKmph;
        private String windGustKmph;
        private String chanceoffog;
        private String visibilityMiles;
        private String heatIndexC;
        private String time;
        private String precipInches;
        private String chanceofwindy;
        private String uvIndex;
        private String tempC;
        private String pressureInches;
        private String heatIndexF;
        private String chanceofremdry;

        private Builder() {
        }

        @Nonnull
        public Builder withWeatherCode(@Nonnull String weatherCode) {
            this.weatherCode = weatherCode;
            return this;
        }

        @Nonnull
        public Builder withChanceofhightemp(@Nonnull String chanceofhightemp) {
            this.chanceofhightemp = chanceofhightemp;
            return this;
        }

        @Nonnull
        public Builder withFeelsLikeF(@Nonnull String feelsLikeF) {
            this.feelsLikeF = feelsLikeF;
            return this;
        }

        @Nonnull
        public Builder withCloudcover(@Nonnull String cloudcover) {
            this.cloudcover = cloudcover;
            return this;
        }

        @Nonnull
        public Builder withWindChillC(@Nonnull String windChillC) {
            this.windChillC = windChillC;
            return this;
        }

        @Nonnull
        public Builder withWindspeedMiles(@Nonnull String windspeedMiles) {
            this.windspeedMiles = windspeedMiles;
            return this;
        }

        @Nonnull
        public Builder withWinddirDegree(@Nonnull String winddirDegree) {
            this.winddirDegree = winddirDegree;
            return this;
        }

        @Nonnull
        public Builder withDewPointC(@Nonnull String dewPointC) {
            this.dewPointC = dewPointC;
            return this;
        }

        @Nonnull
        public Builder withChanceofthunder(@Nonnull String chanceofthunder) {
            this.chanceofthunder = chanceofthunder;
            return this;
        }

        @Nonnull
        public Builder withChanceoffrost(@Nonnull String chanceoffrost) {
            this.chanceoffrost = chanceoffrost;
            return this;
        }

        @Nonnull
        public Builder withDewPointF(@Nonnull String dewPointF) {
            this.dewPointF = dewPointF;
            return this;
        }

        @Nonnull
        public Builder withHumidity(@Nonnull String humidity) {
            this.humidity = humidity;
            return this;
        }

        @Nonnull
        public Builder withWinddir16Point(@Nonnull String winddir16Point) {
            this.winddir16Point = winddir16Point;
            return this;
        }

        @Nonnull
        public Builder withWindChillF(@Nonnull String windChillF) {
            this.windChillF = windChillF;
            return this;
        }

        @Nonnull
        public Builder withWeatherIconUrl(@Nonnull String weatherIconUrl) {
            this.weatherIconUrl = weatherIconUrl;
            return this;
        }

        @Nonnull
        public Builder withTempF(@Nonnull String tempF) {
            this.tempF = tempF;
            return this;
        }

        @Nonnull
        public Builder withPrecipMM(@Nonnull String precipMM) {
            this.precipMM = precipMM;
            return this;
        }

        @Nonnull
        public Builder withWeatherDesc(@Nonnull String weatherDesc) {
            this.weatherDesc = weatherDesc;
            return this;
        }

        @Nonnull
        public Builder withChanceofrain(@Nonnull String chanceofrain) {
            this.chanceofrain = chanceofrain;
            return this;
        }

        @Nonnull
        public Builder withChanceofovercast(@Nonnull String chanceofovercast) {
            this.chanceofovercast = chanceofovercast;
            return this;
        }

        @Nonnull
        public Builder withVisibility(@Nonnull String visibility) {
            this.visibility = visibility;
            return this;
        }

        @Nonnull
        public Builder withPressure(@Nonnull String pressure) {
            this.pressure = pressure;
            return this;
        }

        @Nonnull
        public Builder withChanceofsunshine(@Nonnull String chanceofsunshine) {
            this.chanceofsunshine = chanceofsunshine;
            return this;
        }

        @Nonnull
        public Builder withWindGustMiles(@Nonnull String windGustMiles) {
            this.windGustMiles = windGustMiles;
            return this;
        }

        @Nonnull
        public Builder withChanceofsnow(@Nonnull String chanceofsnow) {
            this.chanceofsnow = chanceofsnow;
            return this;
        }

        @Nonnull
        public Builder withFeelsLikeC(@Nonnull String feelsLikeC) {
            this.feelsLikeC = feelsLikeC;
            return this;
        }

        @Nonnull
        public Builder withWindspeedKmph(@Nonnull String windspeedKmph) {
            this.windspeedKmph = windspeedKmph;
            return this;
        }

        @Nonnull
        public Builder withWindGustKmph(@Nonnull String windGustKmph) {
            this.windGustKmph = windGustKmph;
            return this;
        }

        @Nonnull
        public Builder withChanceoffog(@Nonnull String chanceoffog) {
            this.chanceoffog = chanceoffog;
            return this;
        }

        @Nonnull
        public Builder withVisibilityMiles(@Nonnull String visibilityMiles) {
            this.visibilityMiles = visibilityMiles;
            return this;
        }

        @Nonnull
        public Builder withHeatIndexC(@Nonnull String heatIndexC) {
            this.heatIndexC = heatIndexC;
            return this;
        }

        @Nonnull
        public Builder withTime(@Nonnull String time) {
            this.time = time;
            return this;
        }

        @Nonnull
        public Builder withPrecipInches(@Nonnull String precipInches) {
            this.precipInches = precipInches;
            return this;
        }

        @Nonnull
        public Builder withChanceofwindy(@Nonnull String chanceofwindy) {
            this.chanceofwindy = chanceofwindy;
            return this;
        }

        @Nonnull
        public Builder withUvIndex(@Nonnull String uvIndex) {
            this.uvIndex = uvIndex;
            return this;
        }

        @Nonnull
        public Builder withTempC(@Nonnull String tempC) {
            this.tempC = tempC;
            return this;
        }

        @Nonnull
        public Builder withPressureInches(@Nonnull String pressureInches) {
            this.pressureInches = pressureInches;
            return this;
        }

        @Nonnull
        public Builder withHeatIndexF(@Nonnull String heatIndexF) {
            this.heatIndexF = heatIndexF;
            return this;
        }

        @Nonnull
        public Builder withChanceofremdry(@Nonnull String chanceofremdry) {
            this.chanceofremdry = chanceofremdry;
            return this;
        }

        @Nonnull
        public WeatherDayHourEntity build() {
            return new WeatherDayHourEntity(this);
        }
    }
}
