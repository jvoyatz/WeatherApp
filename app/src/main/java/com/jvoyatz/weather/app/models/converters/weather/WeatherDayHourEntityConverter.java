package com.jvoyatz.weather.app.models.converters.weather;

import com.jvoyatz.weather.app.models.api.weather.Hourly;
import com.jvoyatz.weather.app.models.converters.TypeConverter;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayHourEntity;
import com.jvoyatz.weather.app.util.Objects;

import javax.inject.Inject;

public class WeatherDayHourEntityConverter extends TypeConverter<Hourly, WeatherDayHourEntity> {

    @Inject
    public WeatherDayHourEntityConverter() {
    }

    @Override
    public WeatherDayHourEntity toEntity(Hourly from) {
        if(from == null){
            return null;
        }

        WeatherDayHourEntity.Builder builder = WeatherDayHourEntity.builder()
                .withWeatherCode(from.getWeatherCode())
                .withChanceofhightemp(from.getChanceofhightemp())
                .withFeelsLikeF(from.getFeelsLikeF())
                .withCloudcover(from.getCloudcover())
                .withWindChillC(from.getWindChillC())
                .withWindspeedMiles(from.getWindspeedMiles())
                .withWinddirDegree(from.getWinddirDegree())
                .withDewPointC(from.getDewPointC())
                .withChanceofthunder(from.getChanceofthunder())
                .withChanceoffrost(from.getChanceoffrost())
                .withDewPointF(from.getDewPointF())
                .withHumidity(from.getHumidity())
                .withWinddir16Point(from.getWinddir16Point())
                .withWindChillF(from.getWindChillF())
                .withTempF(from.getTempF())
                .withPrecipMM(from.getPrecipMM())
                .withChanceofrain(from.getChanceofrain())
                .withChanceofovercast(from.getChanceofovercast())
                .withVisibility(from.getVisibility())
                .withPressure(from.getPressure())
                .withChanceofsunshine(from.getChanceofsunshine())
                .withWindGustMiles(from.getWindGustMiles())
                .withChanceofsnow(from.getChanceofsnow())
                .withFeelsLikeC(from.getFeelsLikeC())
                .withWindspeedKmph(from.getWindspeedKmph())
                .withWindGustKmph(from.getWindGustKmph())
                .withChanceoffog(from.getChanceoffog())
                .withVisibilityMiles(from.getVisibilityMiles())
                .withHeatIndexC(from.getHeatIndexC())
                .withTime(from.getTime())
                .withPrecipInches(from.getPrecipInches())
                .withChanceofwindy(from.getChanceofwindy())
                .withUvIndex(from.getUvIndex())
                .withTempC(from.getTempC())
                .withPressureInches(from.getPressureInches())
                .withHeatIndexF(from.getHeatIndexF())
                .withChanceofremdry(from.getChanceofremdry());

        if(!Objects.isEmpty(from.getWeatherIconUrl())){
            builder.withWeatherIconUrl(from.getWeatherIconUrl().get(0).getValue());
        }
        if(!Objects.isEmpty(from.getWeatherDesc())){
            builder.withWeatherDesc(from.getWeatherDesc().get(0).getValue());
        }
        return builder.build();


    }
}
