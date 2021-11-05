package com.jvoyatz.weather.app.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.Constants;
import com.jvoyatz.weather.app.api.WorldWeatherAPI;
import com.jvoyatz.weather.app.storage.db.CityDao;
import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.api.CityResponse;
import com.jvoyatz.weather.app.models.api.config.ApiResponse;
import com.jvoyatz.weather.app.models.api.entities.CityEntity;
import com.jvoyatz.weather.app.models.converters.city.CityConverter;
import com.jvoyatz.weather.app.util.AbsentLiveData;
import com.jvoyatz.weather.app.util.Objects;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
/**
 * Repository class that handles all the tasks regarding Cities entities
 */
public class CityRepository {
    WorldWeatherAPI worldWeatherService;
    AppExecutors appExecutors;
    private final CityDao cityDao;
    private final CityConverter cityConverter;

    @Inject
    public CityRepository(WorldWeatherAPI worldWeatherService, AppExecutors appExecutors, CityDao cityDao, CityConverter cityConverter) {
        this.worldWeatherService = worldWeatherService;
        this.appExecutors = appExecutors;
        this.cityDao = cityDao;
        this.cityConverter = cityConverter;
    }

    /**
     * Searches for a city given as argument if it is not found in the database
     *
     * @param query the city to be searched in the remoteAPI
     * @return LiveData instance, holding the response from the server
     */
    public LiveData<Resource<List<CityEntity>>> searchCity(@NonNull String query){
       return new NetworkBoundResource<List<CityEntity>, CityResponse>(appExecutors){

           @Override
           protected void saveCallResult(@NonNull CityResponse item) {
               try {
                   if(item.getSearchApi() != null){
                       List<CityEntity> cities = cityConverter.toEntities(item.getSearchApi().getResult());
                       cityDao.insertAll(cities);
                   }
               } catch (Exception e) {
                   Timber.e(e);
               }
           }

           @Override
           protected boolean shouldFetch(@Nullable List<CityEntity> data) {
               return Objects.isEmpty(data);
           }

           @NonNull
           @Override
           protected LiveData<List<CityEntity>> loadFromDb() {
               try {
                   return cityDao.findByName(query);
               } catch (Exception e) {
                   Timber.e(e);
                   return AbsentLiveData.create();
               }
           }

           @NonNull
           @Override
           protected LiveData<ApiResponse<CityResponse>> createCall() {
               return worldWeatherService.getCity(query, Constants.QUERY_PARAM_SHOW_LOCALTIME_VALUE_YES, Constants.QUERY_PARAM_FORMAT);
           }
       }.asLiveData();
    }
}
