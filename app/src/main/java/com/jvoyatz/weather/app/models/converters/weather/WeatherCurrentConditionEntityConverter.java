package com.jvoyatz.weather.app.models.converters.weather;

import com.jvoyatz.weather.app.models.api.weather.CurrentCondition;
import com.jvoyatz.weather.app.models.entities.weather.WeatherCurrentConditionEntity;
import com.jvoyatz.weather.app.util.Objects;

import javax.inject.Inject;

public class WeatherCurrentConditionEntityConverter extends TypeConverter<CurrentCondition, WeatherCurrentConditionEntity> {

    @Inject
    public WeatherCurrentConditionEntityConverter() {
    }

    @Override
    public WeatherCurrentConditionEntity toEntity(CurrentCondition from) {
        if(from == null)
            return null;

        WeatherCurrentConditionEntity.Builder builder = WeatherCurrentConditionEntity.builder();

        builder.withPrecipMM(from.getPrecipMM())
                .withObservationTime(from.getObservationTime())
                .withVisibility(from.getVisibility())
                .withWeatherCode(from.getWeatherCode())
                .withFeelsLikeF(from.getFeelsLikeF())
                .withFeelsLikeC(from.getFeelsLikeC())
                .withPressure(from.getPressure())
                .withTempC(from.getTempC())
                .withTempF(from.getTempF())
                .withCloudcover(from.getCloudcover())
                .withWindspeedMiles(from.getWindspeedMiles())
                .withWinddirDegree(from.getWinddirDegree())
                .withHumidity(from.getHumidity())
                .withWindspeedKmph(from.getWindspeedKmph())
                .withVisibilityMiles(from.getVisibilityMiles())
                .withPrecipInches(from.getPrecipInches())
                .withUvIndex(from.getUvIndex())
                .withWinddir16Point(from.getWinddir16Point())
                .withPressureInches(from.getPressureInches());

        if(!Objects.isEmpty(from.getWeatherDesc())){
            builder.withWeatherIconUrl(from.getWeatherIconUrl().get(0).getValue());
        }

        if(!Objects.isEmpty(from.getWeatherDesc())){
            builder.withWeatherDesc(from.getWeatherDesc().get(0).getValue());
        }

        return builder.build();
    }
}
