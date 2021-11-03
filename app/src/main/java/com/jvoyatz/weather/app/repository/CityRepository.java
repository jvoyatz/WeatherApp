package com.jvoyatz.weather.app.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.Constants;
import com.jvoyatz.weather.app.api.WorldWeatherAPI;
import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.api.CityResponse;
import com.jvoyatz.weather.app.models.api.config.ApiResponse;

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

    @Inject
    public CityRepository(WorldWeatherAPI worldWeatherService, AppExecutors appExecutors) {
        this.worldWeatherService = worldWeatherService;
        this.appExecutors = appExecutors;
    }

    /**
     * Searches for a city given as argument if it is not found in the database
     *
     * @param query the city to be searched in the remoteAPI
     * @return LiveData instance, holding the response from the server
     */
    public LiveData<Resource<CityResponse>> searchCity(@NonNull String query){
       return new NetworkBoundResource<CityResponse, CityResponse>(appExecutors){

           @Override
           protected void saveCallResult(@NonNull CityResponse item) {
               Timber.d("loadFromDb() called %s", Thread.currentThread());
           }

           @Override
           protected boolean shouldFetch(@Nullable CityResponse data) {
               Timber.d("loadFromDb() called %s", Thread.currentThread());
               return true;
           }

           @NonNull
           @Override
           protected LiveData<CityResponse> loadFromDb() {
               Timber.d("loadFromDb() called %s", Thread.currentThread());
               return new LiveData<CityResponse>() {
                   @Override
                   protected void onActive() {
                       super.onActive();
                       postValue(null);
                   }
               };
           }

           @NonNull
           @Override
           protected LiveData<ApiResponse<CityResponse>> createCall() {
               Timber.d("loadFromDb() called %s", Thread.currentThread());
               return worldWeatherService.getCity(query, Constants.QUERY_PARAM_SHOW_LOCALTIME_VALUE_YES, Constants.QUERY_PARAM_FORMAT);
           }
       }.asLiveData();
    }
}
