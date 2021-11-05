package com.jvoyatz.weather.app.models.converters.city;

import com.jvoyatz.weather.app.models.api.city.Timezone;
import com.jvoyatz.weather.app.models.api.entities.TimezoneEntity;
import com.jvoyatz.weather.app.models.converters.TypeConverter;

import javax.inject.Inject;

/**
 * Class used to convert remote Timezone instance into TimezoneEntity
 */
public class TimezoneConverter extends TypeConverter<Timezone, TimezoneEntity> {

    @Inject
    public TimezoneConverter() {}

    @Override
    public TimezoneEntity toEntity(Timezone from) {
        if(from != null){
            return TimezoneEntity.builder()
                        .withOffset(from.getOffset())
                        .withZone(from.getZone())
                        .build();
        }
        return null;
    }
}
