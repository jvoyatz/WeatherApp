package com.jvoyatz.weather.app.models.converters.weather;

import android.text.TextUtils;

import com.jvoyatz.weather.app.models.api.weather.WeatherData;
import com.jvoyatz.weather.app.models.api.weather.Request;
import com.jvoyatz.weather.app.models.converters.TypeConverter;
import com.jvoyatz.weather.app.models.entities.weather.WeatherCurrentConditionEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity;
import com.jvoyatz.weather.app.util.Objects;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class WeatherConverter extends TypeConverter<WeatherData, WeatherEntity> {

    private final WeatherDayEntityConverter dayEntityConverter;
    private final WeatherCurrentConditionEntityConverter conditionEntityConverter;

    @Inject
    public WeatherConverter(WeatherDayEntityConverter dayEntityConverter, WeatherCurrentConditionEntityConverter conditionEntityConverter) {
        this.dayEntityConverter = dayEntityConverter;
        this.conditionEntityConverter = conditionEntityConverter;
    }

    @Override
    public WeatherEntity toEntity(WeatherData from) {
        if(from == null){
            return null;
        }

        WeatherEntity.Builder builder = WeatherEntity.builder();

        if(!Objects.isEmpty(from.getWeather())){
            Request weatherRequestItem = from.getRequest().get(0);
            final String type = weatherRequestItem.getType();
            final String query = weatherRequestItem.getQuery();

            if(!TextUtils.isEmpty(query)){
                final String[] strings = query.split(", ");
                if(TextUtils.equals(type, "City") && strings != null && strings.length >= 2){
                    builder.withCity(strings[0]);
                    builder.withCountry(strings[1]);
                }
            }
            builder.withType(type);
        }

        Timber.d("toEntity: " + from.getWeather());
        builder.withWeather(dayEntityConverter.toEntities(from.getWeather()));
        final List<WeatherCurrentConditionEntity> conditionEntities = conditionEntityConverter.toEntities(from.getCurrentCondition());

        if(!Objects.isEmpty(conditionEntities)){
            builder.withCurrentCondition(conditionEntities.get(0));
        }

        builder.withWeather(dayEntityConverter.toEntities(from.getWeather()));

        return builder.build();
    }
}
