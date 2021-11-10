package com.jvoyatz.weather.app.models.converters.weather;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.jvoyatz.weather.app.models.api.weather.Hourly;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayHourEntity;
import com.jvoyatz.weather.app.util.Objects;
import com.jvoyatz.weather.app.util.Utils;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import timber.log.Timber;

public class WeatherDayHourEntityConverter extends TypeConverter<Hourly, WeatherDayHourEntity> {

    private Date parentDate;
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
                .withPrecipInches(from.getPrecipInches())
                .withChanceofwindy(from.getChanceofwindy())
                .withUvIndex(from.getUvIndex())
                .withTempC(from.getTempC())
                .withPressureInches(from.getPressureInches())
                .withHeatIndexF(from.getHeatIndexF())
                .withChanceofremdry(from.getChanceofremdry());

        if(!TextUtils.isEmpty(from.getTime())){
            final String time = transformApiTimeStr(parentDate, from.getTime());
            builder.withTime(time);
        }
        if(!Objects.isEmpty(from.getWeatherIconUrl())){
            builder.withWeatherIconUrl(from.getWeatherIconUrl().get(0).getValue());
        }
        if(!Objects.isEmpty(from.getWeatherDesc())){
            builder.withWeatherDesc(from.getWeatherDesc().get(0).getValue());
        }
        return builder.build();


    }

    /**
     * Api returns times for WeatherDayHourEntity as a String in the
     * following format 1300.
     * We parse and transform it into Java Date
     *
     * @param time api's field
     */
    public static String transformApiTimeStr(Date parentDate, @NonNull String time){
        try {
            time = replaceTimeValue(time);
            if(parentDate != null) {
                final Date date = Utils.mergeHHmmDates(parentDate, time);
                if(date != null) {
                    return Utils.formatFullDate(date);
                }
            }
        } catch (Exception e) {
            Timber.e(e);
        }
        return "";
    }

    public static String replaceTimeValue(String time) {
        if(TextUtils.equals(time, "0")){
            time = time + ":00";
        }else {
            time = time.replace("00", ":00");
        }
        return time;
    }

    public void setParentDate(Date parentDate) {
        this.parentDate = parentDate;
    }
}
