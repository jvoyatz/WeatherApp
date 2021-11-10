package com.jvoyatz.weather.app.models.entities.weather;

import androidx.annotation.NonNull;

import javax.annotation.Nonnull;

public class WeatherDayAstronomyEntity {
    private String moonset;
    private String moonIllumination;
    private String sunrise;
    private String moonPhase;
    private String sunset;
    private String moonrise;

    public WeatherDayAstronomyEntity() {
    }

    private WeatherDayAstronomyEntity(Builder builder) {
        setMoonset(builder.moonset);
        setMoonIllumination(builder.moonIllumination);
        setSunrise(builder.sunrise);
        setMoonPhase(builder.moonPhase);
        setSunset(builder.sunset);
        setMoonrise(builder.moonrise);
    }

    public String getMoonset() {
        return moonset;
    }

    public void setMoonset(String moonset) {
        this.moonset = moonset;
    }

    public String getMoonIllumination() {
        return moonIllumination;
    }

    public void setMoonIllumination(String moonIllumination) {
        this.moonIllumination = moonIllumination;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(String moonPhase) {
        this.moonPhase = moonPhase;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    @NonNull
    @Override
    public String toString() {
        return "WeatherDayAstronomyEntity{" +
                "moonset='" + moonset + '\'' +
                ", moonIllumination='" + moonIllumination + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", moonPhase='" + moonPhase + '\'' +
                ", sunset='" + sunset + '\'' +
                ", moonrise='" + moonrise + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String moonset;
        private String moonIllumination;
        private String sunrise;
        private String moonPhase;
        private String sunset;
        private String moonrise;

        private Builder() {
        }

        @Nonnull
        public Builder withMoonset(@Nonnull String moonset) {
            this.moonset = moonset;
            return this;
        }

        @Nonnull
        public Builder withMoonIllumination(@Nonnull String moonIllumination) {
            this.moonIllumination = moonIllumination;
            return this;
        }

        @Nonnull
        public Builder withSunrise(@Nonnull String sunrise) {
            this.sunrise = sunrise;
            return this;
        }

        @Nonnull
        public Builder withMoonPhase(@Nonnull String moonPhase) {
            this.moonPhase = moonPhase;
            return this;
        }

        @Nonnull
        public Builder withSunset(@Nonnull String sunset) {
            this.sunset = sunset;
            return this;
        }

        @Nonnull
        public Builder withMoonrise(@Nonnull String moonrise) {
            this.moonrise = moonrise;
            return this;
        }

        @Nonnull
        public WeatherDayAstronomyEntity build() {
            return new WeatherDayAstronomyEntity(this);
        }
    }
}
