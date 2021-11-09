package com.jvoyatz.weather.app.models.converters.city;

import static com.jvoyatz.weather.app.util.Objects.isEmpty;

import com.jvoyatz.weather.app.Constants;
import com.jvoyatz.weather.app.models.api.city.CityItem;
import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.models.converters.weather.TypeConverter;

import javax.inject.Inject;

/**
 * Converters CityItem instances to CityEntity objects.
 * see {@link CityEntity}
 * see {@link CityItem}
 */
public class CityConverter extends TypeConverter<CityItem, CityEntity> {

    private final TimezoneConverter timezoneConverter;

    @Inject
    public CityConverter(TimezoneConverter timezoneConverter) {
        this.timezoneConverter = timezoneConverter;
    }

    @Override
    public CityEntity toEntity(CityItem from) {
        if(from != null) {
            return
                CityEntity.builder()
                        .withCountry(isEmpty(from.getCountry()) ? Constants.CHAR_EMPTY:from.getCountry().get(0).getValue())
                        .withName(isEmpty(from.getAreaName()) ? Constants.CHAR_EMPTY:from.getAreaName().get(0).getValue())
                        .withRegion(isEmpty(from.getRegion()) ? Constants.CHAR_EMPTY:from.getRegion().get(0).getValue())
                        .withWeatherUrl(isEmpty(from.getWeatherUrl())? Constants.CHAR_EMPTY:from.getWeatherUrl().get(0).getValue())
                        .withLatitude(from.getLatitude())
                        .withLongitude(from.getLongitude())
                        .withPopulation(from.getPopulation())
                        .withTimezone(timezoneConverter.toEntity(from.getTimezone()))
                    .build();
        }
        return null;
    }
}
