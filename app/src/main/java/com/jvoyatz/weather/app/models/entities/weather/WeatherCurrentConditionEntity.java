package com.jvoyatz.weather.app.models.entities.weather;

import androidx.room.Entity;

import javax.annotation.Nonnull;

@Entity
public class WeatherCurrentConditionEntity {

    private String precipMM;
    private String observationTime;
    private String weatherDesc;
    private String weatherDescEl;
    private String weatherIconUrl;
    private String visibility;
    private String weatherCode;
    private String feelsLikeF;
    private String pressure;
    private String tempC;
    private String tempF;
    private String cloudcover;
    private String windspeedMiles;
    private String winddirDegree;
    private String feelsLikeC;
    private String windspeedKmph;
    private String humidity;
    private String visibilityMiles;
    private String precipInches;
    private String uvIndex;
    private String winddir16Point;
    private String pressureInches;

    public WeatherCurrentConditionEntity() {}

    private WeatherCurrentConditionEntity(Builder builder) {
        setPrecipMM(builder.precipMM);
        setObservationTime(builder.observationTime);
        setWeatherDesc(builder.weatherDesc);
        setWeatherDescEl(builder.weatherDescEl);
        setWeatherIconUrl(builder.weatherIconUrl);
        setVisibility(builder.visibility);
        setWeatherCode(builder.weatherCode);
        setFeelsLikeF(builder.feelsLikeF);
        setPressure(builder.pressure);
        setTempC(builder.tempC);
        setTempF(builder.tempF);
        setCloudcover(builder.cloudcover);
        setWindspeedMiles(builder.windspeedMiles);
        setWinddirDegree(builder.winddirDegree);
        setFeelsLikeC(builder.feelsLikeC);
        setWindspeedKmph(builder.windspeedKmph);
        setHumidity(builder.humidity);
        setVisibilityMiles(builder.visibilityMiles);
        setPrecipInches(builder.precipInches);
        setUvIndex(builder.uvIndex);
        setWinddir16Point(builder.winddir16Point);
        setPressureInches(builder.pressureInches);
    }

    public static Builder builder() {
        return new Builder();
    }


    public String getPrecipMM() {
        return precipMM;
    }

    public void setPrecipMM(String precipMM) {
        this.precipMM = precipMM;
    }

    public String getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public String getWeatherDescEl() {
        return weatherDescEl;
    }

    public void setWeatherDescEl(String weatherDescEl) {
        this.weatherDescEl = weatherDescEl;
    }

    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(String weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getFeelsLikeF() {
        return feelsLikeF;
    }

    public void setFeelsLikeF(String feelsLikeF) {
        this.feelsLikeF = feelsLikeF;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
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

    public String getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(String cloudcover) {
        this.cloudcover = cloudcover;
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

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibilityMiles() {
        return visibilityMiles;
    }

    public void setVisibilityMiles(String visibilityMiles) {
        this.visibilityMiles = visibilityMiles;
    }

    public String getPrecipInches() {
        return precipInches;
    }

    public void setPrecipInches(String precipInches) {
        this.precipInches = precipInches;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getWinddir16Point() {
        return winddir16Point;
    }

    public void setWinddir16Point(String winddir16Point) {
        this.winddir16Point = winddir16Point;
    }

    public String getPressureInches() {
        return pressureInches;
    }

    public void setPressureInches(String pressureInches) {
        this.pressureInches = pressureInches;
    }


    public static final class Builder {
        private String precipMM;
        private String observationTime;
        private String weatherDesc;
        private String weatherDescEl;
        private String weatherIconUrl;
        private String visibility;
        private String weatherCode;
        private String feelsLikeF;
        private String pressure;
        private String tempC;
        private String tempF;
        private String cloudcover;
        private String windspeedMiles;
        private String winddirDegree;
        private String feelsLikeC;
        private String windspeedKmph;
        private String humidity;
        private String visibilityMiles;
        private String precipInches;
        private String uvIndex;
        private String winddir16Point;
        private String pressureInches;

        private Builder() {
        }

        @Nonnull
        public Builder withPrecipMM(@Nonnull String precipMM) {
            this.precipMM = precipMM;
            return this;
        }

        @Nonnull
        public Builder withObservationTime(@Nonnull String observationTime) {
            this.observationTime = observationTime;
            return this;
        }

        @Nonnull
        public Builder withWeatherDesc(@Nonnull String weatherDesc) {
            this.weatherDesc = weatherDesc;
            return this;
        }

        @Nonnull
        public Builder withWeatherDescEl(@Nonnull String weatherDescEl) {
            this.weatherDescEl = weatherDescEl;
            return this;
        }

        @Nonnull
        public Builder withWeatherIconUrl(@Nonnull String weatherIconUrl) {
            this.weatherIconUrl = weatherIconUrl;
            return this;
        }

        @Nonnull
        public Builder withVisibility(@Nonnull String visibility) {
            this.visibility = visibility;
            return this;
        }

        @Nonnull
        public Builder withWeatherCode(@Nonnull String weatherCode) {
            this.weatherCode = weatherCode;
            return this;
        }

        @Nonnull
        public Builder withFeelsLikeF(@Nonnull String feelsLikeF) {
            this.feelsLikeF = feelsLikeF;
            return this;
        }

        @Nonnull
        public Builder withPressure(@Nonnull String pressure) {
            this.pressure = pressure;
            return this;
        }

        @Nonnull
        public Builder withTempC(@Nonnull String tempC) {
            this.tempC = tempC;
            return this;
        }

        @Nonnull
        public Builder withTempF(@Nonnull String tempF) {
            this.tempF = tempF;
            return this;
        }

        @Nonnull
        public Builder withCloudcover(@Nonnull String cloudcover) {
            this.cloudcover = cloudcover;
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
        public Builder withHumidity(@Nonnull String humidity) {
            this.humidity = humidity;
            return this;
        }

        @Nonnull
        public Builder withVisibilityMiles(@Nonnull String visibilityMiles) {
            this.visibilityMiles = visibilityMiles;
            return this;
        }

        @Nonnull
        public Builder withPrecipInches(@Nonnull String precipInches) {
            this.precipInches = precipInches;
            return this;
        }

        @Nonnull
        public Builder withUvIndex(@Nonnull String uvIndex) {
            this.uvIndex = uvIndex;
            return this;
        }

        @Nonnull
        public Builder withWinddir16Point(@Nonnull String winddir16Point) {
            this.winddir16Point = winddir16Point;
            return this;
        }

        @Nonnull
        public Builder withPressureInches(@Nonnull String pressureInches) {
            this.pressureInches = pressureInches;
            return this;
        }

        @Nonnull
        public WeatherCurrentConditionEntity build() {
            return new WeatherCurrentConditionEntity(this);
        }
    }

    @Override
    public String toString() {
        return "WeatherCurrentConditionEntity{" +
                "precipMM='" + precipMM + '\'' +
                ", observationTime='" + observationTime + '\'' +
                ", weatherDesc='" + weatherDesc + '\'' +
                ", weatherDescEl='" + weatherDescEl + '\'' +
                ", weatherIconUrl='" + weatherIconUrl + '\'' +
                ", visibility='" + visibility + '\'' +
                ", weatherCode='" + weatherCode + '\'' +
                ", feelsLikeF='" + feelsLikeF + '\'' +
                ", pressure='" + pressure + '\'' +
                ", tempC='" + tempC + '\'' +
                ", tempF='" + tempF + '\'' +
                ", cloudcover='" + cloudcover + '\'' +
                ", windspeedMiles='" + windspeedMiles + '\'' +
                ", winddirDegree='" + winddirDegree + '\'' +
                ", feelsLikeC='" + feelsLikeC + '\'' +
                ", windspeedKmph='" + windspeedKmph + '\'' +
                ", humidity='" + humidity + '\'' +
                ", visibilityMiles='" + visibilityMiles + '\'' +
                ", precipInches='" + precipInches + '\'' +
                ", uvIndex='" + uvIndex + '\'' +
                ", winddir16Point='" + winddir16Point + '\'' +
                ", pressureInches='" + pressureInches + '\'' +
                '}';
    }
}
