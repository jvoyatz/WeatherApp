package com.jvoyatz.weather.app.models.converters.weather;

import android.text.TextUtils;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayEntity;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

@ProvidedTypeConverter
public class WeatherTypeListConverter {
    public final Gson gson;

    @Inject
    public WeatherTypeListConverter(Gson gson) {
        this.gson = gson;
    }

    @TypeConverter
    public List<WeatherDayEntity> stringToList(String string){
        if(TextUtils.isEmpty(string))
            return Collections.emptyList();

        final TypeToken<List<WeatherDayEntity>> typeToken = new TypeToken<List<WeatherDayEntity>>() {};
        return gson.fromJson(string, typeToken.getType());
    }
    @TypeConverter
    public String listToString(List<WeatherDayEntity> dayEntities){
        return gson.toJson(dayEntities);
    }
}
