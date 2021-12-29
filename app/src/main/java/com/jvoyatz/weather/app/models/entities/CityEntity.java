package com.jvoyatz.weather.app.models.entities;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;

import java.util.Objects;


@Entity(primaryKeys = {"_id", "name", "region", "country"})
public class CityEntity {
    //@PrimaryKey(autoGenerate = true)
    public int _id;

    @NonNull
    private String name;
    @NonNull
    private String country;
    @NonNull
    private String region;
    private String latitude;
    private String longitude;
    private String population;
    private String weatherUrl;

    @Embedded
    private TimezoneEntity timezone;

    private boolean isFavorite;

    public CityEntity() {
    }

    public CityEntity(Builder builder) {
        name = builder.name;
        country = builder.country;
        region = builder.region;
        latitude = builder.latitude;
        longitude = builder.longitude;
        population = builder.population;
        weatherUrl = builder.weatherUrl;
        timezone = builder.timezone;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setCountry(@NonNull String country) {
        this.country = country;
    }

    public void setRegion(@NonNull String region) {
        this.region = region;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public void setWeatherUrl(String weatherUrl) {
        this.weatherUrl = weatherUrl;
    }

    public void setTimezone(TimezoneEntity timezone) {
        this.timezone = timezone;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    @NonNull
    public String getRegion() {
        return region;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPopulation() {
        return population;
    }

    public String getWeatherUrl() {
        return weatherUrl;
    }

    public TimezoneEntity getTimezone() {
        return timezone;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "CityEntity{" +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }

    @NonNull

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityEntity that = (CityEntity) o;

        return isFavorite == that.isFavorite
                && TextUtils.equals(name, that.name)
                && TextUtils.equals(country, that.country)
                && TextUtils.equals(region, that.region)
                && TextUtils.equals(latitude, that.latitude)
                && TextUtils.equals(longitude, that.longitude)
                && TextUtils.equals(population, that.population)
                && TextUtils.equals(weatherUrl, that.weatherUrl)
                && timezone != null && timezone.equals(that.timezone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country, region, latitude, longitude, population, weatherUrl, timezone, isFavorite);
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {
        String name;
        String country;
        String region;
        String latitude;
        String longitude;
        String population;
        String weatherUrl;
        TimezoneEntity timezone;

        public Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder withLatitude(String latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withLongitude(String longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder withPopulation(String population) {
            this.population = population;
            return this;
        }

        public Builder withWeatherUrl(String weatherUrls) {
            this.weatherUrl = weatherUrls;
            return this;
        }

        public Builder withTimezone(TimezoneEntity timezone) {
            this.timezone = timezone;
            return this;
        }

        public CityEntity build() {
            return new CityEntity(this);
        }
    }
}
