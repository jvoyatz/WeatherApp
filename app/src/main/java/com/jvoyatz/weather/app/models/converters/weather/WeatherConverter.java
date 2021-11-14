package com.jvoyatz.weather.app.models.converters.weather;

import android.text.TextUtils;
import android.util.Log;

import com.jvoyatz.weather.app.models.api.weather.Request;
import com.jvoyatz.weather.app.models.api.weather.WeatherData;
import com.jvoyatz.weather.app.models.entities.weather.WeatherCurrentConditionEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherTimezoneEntity;
import com.jvoyatz.weather.app.util.Objects;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class WeatherConverter extends TypeConverter<WeatherData, WeatherEntity> {

    private final WeatherDayEntityConverter dayEntityConverter;
    private final WeatherCurrentConditionEntityConverter conditionEntityConverter;
    private final WeatherTimezoneConverter weatherTimezoneConverter;

    @Inject
    public WeatherConverter(WeatherDayEntityConverter dayEntityConverter, WeatherCurrentConditionEntityConverter conditionEntityConverter, WeatherTimezoneConverter weatherTimezoneConverter) {
        this.dayEntityConverter = dayEntityConverter;
        this.conditionEntityConverter = conditionEntityConverter;
        this.weatherTimezoneConverter = weatherTimezoneConverter;
    }

    @Override
    public WeatherEntity toEntity(WeatherData from) {
        if (from == null) {
            return null;
        }

        WeatherEntity.Builder builder = WeatherEntity.builder();

        if (!Objects.isEmpty(from.getWeather())) {
            Request weatherRequestItem = from.getRequest().get(0);
            final String type = weatherRequestItem.getType();
            String query = weatherRequestItem.getQuery();

            if (!TextUtils.isEmpty(query)) {
                String[] strings = query.split(", ");
                if (TextUtils.equals(type, "City") && strings.length >= 2) {
                    builder.withCity(strings[0]);
                    builder.withCountry(strings[1]);
                } else if (TextUtils.equals(type, "LatLon")) {//split 27.00 and 32.00
                    query = query.replace("Lat", "").replace("Lon", " ").trim();
                    strings = query.split(" and ");
                    if (strings.length >= 2) {
                        builder.withCity(strings[0] + "," + strings[1]);
                    }
                }
            }
            builder.withType(type);
        }
        final List<WeatherTimezoneEntity> weatherTimezoneEntities = weatherTimezoneConverter.toEntities(from.getTimeZone());
        if (weatherTimezoneEntities != null)
            builder.withTimezone(weatherTimezoneEntities.get(0));
        builder.withWeather(dayEntityConverter.toEntities(from.getWeather()));
        final List<WeatherCurrentConditionEntity> conditionEntities = conditionEntityConverter.toEntities(from.getCurrentCondition());

        if (!Objects.isEmpty(conditionEntities)) {
            builder.withCurrentCondition(conditionEntities.get(0));
        }

        builder.withWeather(dayEntityConverter.toEntities(from.getWeather()));

        return builder.build();
    }
}
