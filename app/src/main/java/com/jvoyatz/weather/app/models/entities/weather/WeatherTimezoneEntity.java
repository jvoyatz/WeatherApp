package com.jvoyatz.weather.app.models.entities.weather;

import androidx.room.Entity;

import javax.annotation.Nonnull;

@Entity
public class WeatherTimezoneEntity {
    private String localtime;
    private String utcOffset;
    private String zone;

    public WeatherTimezoneEntity() {
    }

    private WeatherTimezoneEntity(Builder builder) {
        this.localtime = builder.localtime;
        this.utcOffset = builder.utcOffset;
        this.zone = builder.zone;
    }

    public static Builder builder() {
        return new Builder();
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

    public static final class Builder {
        private String localtime;
        private String utcOffset;
        private String zone;

        private Builder() {
        }

        @Nonnull
        public Builder withLocaltime(@Nonnull String localtime) {
            this.localtime = localtime;
            return this;
        }

        @Nonnull
        public Builder withUtcOffset(@Nonnull String utcOffset) {
            this.utcOffset = utcOffset;
            return this;
        }

        @Nonnull
        public Builder withZone(@Nonnull String zone) {
            this.zone = zone;
            return this;
        }

        @Nonnull
        public WeatherTimezoneEntity build() {
            return new WeatherTimezoneEntity(this);
        }
    }
}
