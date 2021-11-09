package com.jvoyatz.weather.app.repository;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.api.WorldWeatherAPI;
import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.api.config.ApiResponse;
import com.jvoyatz.weather.app.models.api.weather.WeatherResponse;
import com.jvoyatz.weather.app.models.converters.weather.WeatherConverter;
import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity;
import com.jvoyatz.weather.app.storage.db.WeatherDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * Handles all the data needed to be fetched regarding
 * weather forecast.
 */

@Singleton
public class WeatherRepository {
    WorldWeatherAPI worldWeatherService;
    private final AppExecutors appExecutors;
    private final WeatherConverter weatherConverter;
    private final WeatherDao weatherDao;

    @Inject
    public WeatherRepository(WorldWeatherAPI worldWeatherService, AppExecutors appExecutors, WeatherConverter weatherConverter, WeatherDao weatherDao) {
        this.worldWeatherService = worldWeatherService;
        this.appExecutors = appExecutors;
        this.weatherConverter = weatherConverter;
        this.weatherDao = weatherDao;
    }

    /**
     * Fetches the weather forecast for the given value in the argument
     *
     * @param city the city we want to fetch weather forecast for
     * @return Livedata holding a resource of WeatherEntity
     */
    public LiveData<Resource<WeatherEntity>> getCityWeatherForecast(@NonNull CityEntity city){
        return new NetworkBoundResource<WeatherEntity, WeatherResponse>(appExecutors){

            @Override
            protected void saveCallResult(@NonNull WeatherResponse item) {
                try{
                    final WeatherEntity weatherEntity = weatherConverter.toEntity(item.getData());
                    //response does not contain region
                    weatherEntity.setRegion(city.getRegion());
                    weatherEntity.setCity(city.getName());
                    weatherEntity.setCountry(city.getCountry());

                    weatherDao.insert(weatherEntity);
                }catch (Exception e){
                    Timber.e(e);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable WeatherEntity data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<WeatherEntity> loadFromDb() {
                return weatherDao.getWeatherEntity(city.getName(), city.getRegion(), city.getCountry());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<WeatherResponse>> createCall() {
                final StringBuilder query = new StringBuilder();
                if(!TextUtils.isEmpty(city.getLatitude()) && !TextUtils.isEmpty(city.getLongitude())) {
                    query
                        .append(city.getLatitude())
                        .append(", ")
                        .append(city.getLongitude());
                }else if(!TextUtils.isEmpty(city.getName())){
                    query.append(city.getName());
                    if(!TextUtils.isEmpty(city.getRegion())){
                        query.append(", ").append(city.getRegion());
                    }
                    if(!TextUtils.isEmpty(city.getCountry())){
                        query.append(", ").append(city.getCountry());
                    }
                }
                return worldWeatherService.getWeatherForecast(query.toString(), 5, "yes", "json");
            }
        }.asLiveData();
    }
}
