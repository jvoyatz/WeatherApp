package com.jvoyatz.weather.app.models.converters.weather;

import com.jvoyatz.weather.app.models.api.weather.TimeZone;
import com.jvoyatz.weather.app.models.entities.weather.WeatherTimezoneEntity;

import javax.inject.Inject;

/**
 * Converts a Timezone instance from api to WeatherTimezonEntity
 */
public class WeatherTimezoneConverter extends TypeConverter<TimeZone, WeatherTimezoneEntity> {

    @Inject
    public WeatherTimezoneConverter() {
    }

    @Override
    public WeatherTimezoneEntity toEntity(TimeZone from) {
        if(from == null){
            return null;
        }

        return WeatherTimezoneEntity.builder()
                .withLocaltime(from.getLocaltime())
                .withZone(from.getZone())
                .withUtcOffset(from.getUtcOffset())
                .build();
    }
}
