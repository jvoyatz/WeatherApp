package com.jvoyatz.weather.app.models.converters.weather;

import com.jvoyatz.weather.app.models.api.weather.Astronomy;
import com.jvoyatz.weather.app.models.converters.TypeConverter;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayAstronomyEntity;

import javax.inject.Inject;

public class WeatherDayAstronomyEntityConverter extends TypeConverter<Astronomy, WeatherDayAstronomyEntity> {

    @Inject
    public WeatherDayAstronomyEntityConverter() {
    }

    @Override
    public WeatherDayAstronomyEntity toEntity(Astronomy from) {
        if(from == null){
            return null;
        }

        return WeatherDayAstronomyEntity.builder()
                .withMoonPhase(from.getMoonPhase())
                .withMoonrise(from.getMoonrise())
                .withMoonset(from.getMoonset())
                .withSunrise(from.getSunrise())
                .withSunset(from.getSunset())
                .withMoonIllumination(from.getMoonIllumination())
                .build();
    }
}
