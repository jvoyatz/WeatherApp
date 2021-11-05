package com.jvoyatz.weather.app.models.api.entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

@Fts4
@Entity
public class CityEntity {
    // Specifying a primary key for an FTS-table-backed entity is optional, but
    // if you include one, it must use this type and column name.
    @PrimaryKey
  //  @ColumnInfo(name = "rowid")
    public int rowid;

    private String name;
    private String country;
    private String region;
    private String latitude;
    private String longitude;
    private String population;
    private String weatherUrl;
    @Embedded
    private TimezoneEntity timezone;

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
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setRegion(String region) {
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

    public int getRowid() {
        return rowid;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

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

    @Override
    public String toString() {
        return "CityEntity{" +
                "rowid=" + rowid +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", population='" + population + '\'' +
                ", weatherUrl='" + weatherUrl + '\'' +
                ", timezone=" + timezone +
                '}';
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
