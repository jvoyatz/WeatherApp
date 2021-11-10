package com.jvoyatz.weather.app.models.entities.weather;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.List;

import javax.annotation.Nonnull;

@Entity
public class WeatherDayEntity {

    private String date;
    private String sunHour;
    private String mintempF;
    private String avgtempF;
    private String mintempC;
    private String totalSnowCm;
    private String maxtempF;
    private List<WeatherDayHourEntity> hourly;
    private String avgtempC;
    private List<WeatherDayAstronomyEntity> astronomy;
    private String uvIndex;
    private String maxtempC;

    public WeatherDayEntity() {
    }

    private WeatherDayEntity(Builder builder) {
        setDate(builder.date);
        setSunHour(builder.sunHour);
        setMintempF(builder.mintempF);
        setAvgtempF(builder.avgtempF);
        setMintempC(builder.mintempC);
        setTotalSnowCm(builder.totalSnowCm);
        setMaxtempF(builder.maxtempF);
        setHourly(builder.hourly);
        setAvgtempC(builder.avgtempC);
        setAstronomy(builder.astronomy);
        setUvIndex(builder.uvIndex);
        setMaxtempC(builder.maxtempC);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSunHour() {
        return sunHour;
    }

    public void setSunHour(String sunHour) {
        this.sunHour = sunHour;
    }

    public String getMintempF() {
        return mintempF;
    }

    public void setMintempF(String mintempF) {
        this.mintempF = mintempF;
    }

    public String getAvgtempF() {
        return avgtempF;
    }

    public void setAvgtempF(String avgtempF) {
        this.avgtempF = avgtempF;
    }

    public String getMintempC() {
        return mintempC;
    }

    public void setMintempC(String mintempC) {
        this.mintempC = mintempC;
    }

    public String getTotalSnowCm() {
        return totalSnowCm;
    }

    public void setTotalSnowCm(String totalSnowCm) {
        this.totalSnowCm = totalSnowCm;
    }

    public String getMaxtempF() {
        return maxtempF;
    }

    public void setMaxtempF(String maxtempF) {
        this.maxtempF = maxtempF;
    }

    public List<WeatherDayHourEntity> getHourly() {
        return hourly;
    }

    public void setHourly(List<WeatherDayHourEntity> hourly) {
        this.hourly = hourly;
    }

    public String getAvgtempC() {
        return avgtempC;
    }

    public void setAvgtempC(String avgtempC) {
        this.avgtempC = avgtempC;
    }

    public List<WeatherDayAstronomyEntity> getAstronomy() {
        return astronomy;
    }

    public void setAstronomy(List<WeatherDayAstronomyEntity> astronomy) {
        this.astronomy = astronomy;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getMaxtempC() {
        return maxtempC;
    }

    public void setMaxtempC(String maxtempC) {
        this.maxtempC = maxtempC;
    }

    @NonNull
    @Override
    public String toString() {
        return "WeatherDayEntity{" +
                "date='" + date + '\'' +
                ", sunHour='" + sunHour + '\'' +
                ", mintempF='" + mintempF + '\'' +
                ", avgtempF='" + avgtempF + '\'' +
                ", mintempC='" + mintempC + '\'' +
                ", totalSnowCm='" + totalSnowCm + '\'' +
                ", maxtempF='" + maxtempF + '\'' +
                ", hourly=" + hourly +
                ", avgtempC='" + avgtempC + '\'' +
                ", astronomy=" + astronomy +
                ", uvIndex='" + uvIndex + '\'' +
                ", maxtempC='" + maxtempC + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String date;
        private String sunHour;
        private String mintempF;
        private String avgtempF;
        private String mintempC;
        private String totalSnowCm;
        private String maxtempF;
        private List<WeatherDayHourEntity> hourly;
        private String avgtempC;
        private List<WeatherDayAstronomyEntity> astronomy;
        private String uvIndex;
        private String maxtempC;

        private Builder() {
        }

        @Nonnull
        public Builder withDate(@Nonnull String date) {
            this.date = date;
            return this;
        }

        @Nonnull
        public Builder withSunHour(@Nonnull String sunHour) {
            this.sunHour = sunHour;
            return this;
        }

        @Nonnull
        public Builder withMintempF(@Nonnull String mintempF) {
            this.mintempF = mintempF;
            return this;
        }

        @Nonnull
        public Builder withAvgtempF(@Nonnull String avgtempF) {
            this.avgtempF = avgtempF;
            return this;
        }

        @Nonnull
        public Builder withMintempC(@Nonnull String mintempC) {
            this.mintempC = mintempC;
            return this;
        }

        @Nonnull
        public Builder withTotalSnowCm(@Nonnull String totalSnowCm) {
            this.totalSnowCm = totalSnowCm;
            return this;
        }

        @Nonnull
        public Builder withMaxtempF(@Nonnull String maxtempF) {
            this.maxtempF = maxtempF;
            return this;
        }

        @Nonnull
        public Builder withHourly(@Nonnull List<WeatherDayHourEntity> hourly) {
            this.hourly = hourly;
            return this;
        }

        @Nonnull
        public Builder withAvgtempC(@Nonnull String avgtempC) {
            this.avgtempC = avgtempC;
            return this;
        }

        @Nonnull
        public Builder withAstronomy(@Nonnull List<WeatherDayAstronomyEntity> astronomy) {
            this.astronomy = astronomy;
            return this;
        }

        @Nonnull
        public Builder withUvIndex(@Nonnull String uvIndex) {
            this.uvIndex = uvIndex;
            return this;
        }

        @Nonnull
        public Builder withMaxtempC(@Nonnull String maxtempC) {
            this.maxtempC = maxtempC;
            return this;
        }

        @Nonnull
        public WeatherDayEntity build() {
            return new WeatherDayEntity(this);
        }
    }
}
