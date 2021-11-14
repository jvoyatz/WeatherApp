package com.jvoyatz.weather.app.repository;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.api.WorldWeatherAPI;
import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.api.config.ApiResponse;
import com.jvoyatz.weather.app.models.api.weather.WeatherResponse;
import com.jvoyatz.weather.app.models.converters.weather.WeatherConverter;
import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity;
import com.jvoyatz.weather.app.storage.db.WeatherDao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
              /*  if(!TextUtils.isEmpty(city.getLatitude()) && !TextUtils.isEmpty(city.getLongitude())) {
                    query
                        .append(city.getLatitude())
                        .append(", ")
                        .append(city.getLongitude());
                }else*/
                if(!TextUtils.isEmpty(city.getName())){
                    query.append(city.getName());
                    if(!TextUtils.isEmpty(city.getRegion())){
                        query.append(", ").append(city.getRegion());
                    }
                    if(!TextUtils.isEmpty(city.getCountry())){
                        query.append(", ").append(city.getCountry());
                    }
                }

                Map<String, String> map = new HashMap<String, String>(){{
                        put("format", "json");
                        put("q", query.toString());
                        put("num_of_days", Integer.toString(6));
                        put("showLocalTime", "yes");
                        put("tp", "1");
                    }
                };
                return worldWeatherService.getWeatherForecast(map);
            }
        }.asLiveData();
    }


    /**
     * Gets the weather for a specified date given as an argument.
     * Does not save the response in the db.
     */
    public LiveData<Resource<WeatherEntity>> getCityWeatherForecastForDate(@NonNull String query, @NonNull String date){
        Map<String, String> map = new LinkedHashMap<String, String>(){{
                put("format", "json");
                put("date", date);
                put("q", query);
                put("tp", "1");
               // put("showlocaltime", "yes");
            }
        };
        return getWeatherForecast(map);
    }

    /**
     * Fetches the weather forecast for the given coordinates
     */
    public LiveData<Resource<WeatherEntity>> getWeatherForecastLatLon(double lat, double lon){
        Map<String, String> map = new LinkedHashMap<String, String>() {
            {
                put("format", "json");
                put("q", lat + "," + lon);
                put("num_of_days", Integer.toString(6));
                put("showlocaltime", "yes");
                put("tp", "1");

            }
        };

        return getWeatherForecast(map);
    }
    /**
     * Gets the weather for a specified date given as an argument.
     * Does not save the response in the db.
     */
    public LiveData<Resource<WeatherEntity>> getWeatherForecast(@NonNull Map<String, String> queryMap) {
        MediatorLiveData<Resource<WeatherEntity>> liveData = new MediatorLiveData<>();

        final LiveData<ApiResponse<WeatherResponse>> weatherForecastCall = worldWeatherService.getWeatherForecast(queryMap);
        liveData.addSource(weatherForecastCall, apiResponse -> {
            liveData.removeSource(weatherForecastCall);
            if (apiResponse instanceof ApiResponse.ApiSuccessResponse) {
                try {
                    final WeatherResponse body = ((ApiResponse.ApiSuccessResponse<WeatherResponse>) apiResponse).getBody();
                    final WeatherEntity weatherEntity = weatherConverter.toEntity(body.getData());
                    liveData.postValue(Resource.success(weatherEntity));
                } catch (Exception e) {
                    liveData.postValue(Resource.error(e.getMessage(), null, null));
                }
            } else if (apiResponse instanceof ApiResponse.ApiErrorResponse) {
                final ApiResponse.ApiErrorResponse<WeatherResponse> errorResponse = (ApiResponse.ApiErrorResponse<WeatherResponse>) apiResponse;
                liveData.postValue(Resource.error(errorResponse.getErrorMessage(), null, errorResponse.getException()));
            }
        });
        return liveData;
    }
}
