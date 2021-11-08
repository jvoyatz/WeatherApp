package com.jvoyatz.weather.app.models.entities.weather;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.jvoyatz.weather.app.models.converters.WeatherTypeListConverter;

import java.util.List;

import javax.annotation.Nonnull;

@Entity(primaryKeys = {"city", "country"})
public class WeatherEntity {

    private String type;
    @NonNull
    private String city;
    @NonNull
    private String region;
    @NonNull
    private String country;
    @Embedded
    private WeatherCurrentConditionEntity currentCondition;
  //  @Embedded
    @TypeConverters(WeatherTypeListConverter.class)
    private List<WeatherDayEntity> weather;

    public WeatherEntity() { }

    private WeatherEntity(Builder builder) {
        setType(builder.type);
        setCity(builder.city);
        setRegion(builder.region);
        setCountry(builder.country);
        setCurrentCondition(builder.currentCondition);
        setWeather(builder.weather);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    @NonNull
    public String getRegion() {
        return region;
    }

    public void setRegion(@NonNull String region) {
        this.region = region;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    public void setCountry(@NonNull String country) {
        this.country = country;
    }

    public WeatherCurrentConditionEntity getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(WeatherCurrentConditionEntity currentCondition) {
        this.currentCondition = currentCondition;
    }

    public List<WeatherDayEntity> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherDayEntity> weather) {
        this.weather = weather;
    }

    @NonNull
    @Override
    public String toString() {
        return "WeatherEntity{" +
                "type='" + type + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", currentCondition=" + currentCondition +
                ", weather=" + weather +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String type;
        private String city;
        private String region;
        private String country;
        private WeatherCurrentConditionEntity currentCondition;
        private List<WeatherDayEntity> weather;

        private Builder() {
        }

        @Nonnull
        public Builder withType(@Nonnull String type) {
            this.type = type;
            return this;
        }

        @Nonnull
        public Builder withCity(@Nonnull String city) {
            this.city = city;
            return this;
        }

        @Nonnull
        public Builder withRegion(@Nonnull String region) {
            this.region = region;
            return this;
        }

        @Nonnull
        public Builder withCountry(@Nonnull String country) {
            this.country = country;
            return this;
        }

        @Nonnull
        public Builder withCurrentCondition(@Nonnull WeatherCurrentConditionEntity currentCondition) {
            this.currentCondition = currentCondition;
            return this;
        }

        @Nonnull
        public Builder withWeather(@Nonnull List<WeatherDayEntity> weather) {
            this.weather = weather;
            return this;
        }

        @Nonnull
        public WeatherEntity build() {
            return new WeatherEntity(this);
        }
    }
}
