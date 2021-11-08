package com.jvoyatz.weather.app.models.converters.weather;

import com.jvoyatz.weather.app.models.api.weather.Weather;
import com.jvoyatz.weather.app.models.converters.TypeConverter;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayEntity;

import javax.inject.Inject;

public class WeatherDayEntityConverter extends TypeConverter<Weather, WeatherDayEntity> {
    private final WeatherDayHourEntityConverter dayHourEntityConverter;
    private final WeatherDayAstronomyEntityConverter astronomyEntityConverter;

    @Inject
    public WeatherDayEntityConverter(WeatherDayHourEntityConverter dayHourEntityConverter, WeatherDayAstronomyEntityConverter astronomyEntityConverter) {
        this.dayHourEntityConverter = dayHourEntityConverter;
        this.astronomyEntityConverter = astronomyEntityConverter;
    }

    @Override
    public WeatherDayEntity toEntity(Weather from) {
        if(from == null){
            return null;
        }

        WeatherDayEntity.Builder builder = WeatherDayEntity.builder();
        builder.withDate(from.getDate())
                .withSunHour(from.getSunHour())
                .withMintempF(from.getMintempF())
                .withAvgtempF(from.getAvgtempF())
                .withMintempC(from.getMintempC())
                .withTotalSnowCm(from.getTotalSnowCm())
                .withMaxtempF(from.getMaxtempF())
                .withAvgtempC(from.getAvgtempC())
                .withUvIndex(from.getUvIndex())
                .withMaxtempC(from.getMaxtempC());

        if(from.getHourly() != null){
            builder.withHourly(dayHourEntityConverter.toEntities(from.getHourly()));
        }
        if(from.getAstronomy() != null){
            builder.withAstronomy(astronomyEntityConverter.toEntities(from.getAstronomy()));
        }

        return builder.build();
    }
}
