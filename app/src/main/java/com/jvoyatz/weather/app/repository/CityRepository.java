package com.jvoyatz.weather.app.repository;

import android.database.Cursor;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.Constants;
import com.jvoyatz.weather.app.api.WorldWeatherAPI;
import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.api.CityResponse;
import com.jvoyatz.weather.app.models.api.config.ApiResponse;
import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.models.converters.city.CityConverter;
import com.jvoyatz.weather.app.storage.db.CityDao;
import com.jvoyatz.weather.app.util.CursorLiveData;

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
/*    public LiveData<Resource<List<CityEntity>>> searchCity(@NonNull String query){
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
    }*/

    /**
     * Checks whether there are cities matching user's in the local database.
     * If not, then it tries directly to fetch from the api.
     * Otherwise, dispatches directly everything found in the db
     *
     * @param query city to be found
     * @return LiveData which holds a cursor wrapped in Resource instance.
     */
    public LiveData<Resource<Cursor>> searchForCitySuggestions(@NonNull String query){
        return new NetworkBoundResource<Cursor, CityResponse>(appExecutors){
            @Override
            protected void saveCallResult(@NonNull CityResponse item) {
                try {
                    if(item.getSearchApi() != null){
                        List<CityEntity> cities = cityConverter.toEntities(item.getSearchApi().getResult());
                        cityDao.insertAll(cities);
                    }
                } catch (Exception e) {Timber.e(e);}
            }

            @Override
            protected boolean shouldFetch(@Nullable Cursor data) {
                return data == null || (data.isClosed() || data.getCount() == 0);
            }

            @NonNull
            @Override
            protected LiveData<Cursor> loadFromDb() {
                return new CursorLiveData(appExecutors, new CursorLiveData.DataLoader() {
                    @Override
                    public Cursor load() {
                        try {
                            return cityDao.findByNameCursor(query);
                        } catch (Exception e) {
                            Timber.e(e);
                            return null;
                        }
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CityResponse>> createCall() {
                return worldWeatherService.getCity(query, Constants.QUERY_PARAM_SHOW_LOCALTIME_VALUE_YES, Constants.QUERY_PARAM_FORMAT);
            }
        }.asLiveData();
    }

    /**
     * Updates the value {@link CityEntity} isFavorite column in the database
     *
     * @param name String, city name
     * @param region String, the region where city belongs
     * @param country String, the country of the city
     * @return LiveData<Pair<Boolean, Boolean>> the first field represents the status of the task (success or not),
     * where the second let us know the current isFavorite value as chosen by the user.
     *
     * In a case of Exception, the second value gets ignored.
     */
    public LiveData<Pair<Boolean, Boolean>> updateFavoriteCity(@NonNull String name, @NonNull String region, @NonNull String country) {
        MediatorLiveData<Pair<Boolean, Boolean>> resultLiveData = new MediatorLiveData<>();

        LiveData<CityEntity> dbSource = cityDao.findByMultipleCriteria(name, region, country);

        resultLiveData.addSource(dbSource, new Observer<CityEntity>() {
            @Override
            public void onChanged(CityEntity cityEntity) {
                resultLiveData.removeSource(dbSource);
                if(cityEntity != null){
                    appExecutors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                cityEntity.setFavorite(!cityEntity.isFavorite());
                                cityDao.insert(cityEntity);
                                resultLiveData.postValue(Pair.create(true, cityEntity.isFavorite()));
                            } catch (Exception e) {
                                Timber.e(e);
                                resultLiveData.postValue(Pair.create(false, false));
                            }
                        }
                    });
                }
            }
        });


        return resultLiveData;
    }
// used for suggestions, commented out block of code
//    MediatorLiveData<Resource<Cursor>> result = new MediatorLiveData<>();
//    CursorLiveData cursorLiveData = new CursorLiveData(appExecutors, new CursorLiveData.DataLoader() {
//        @Override
//        public Cursor load() {
//            try {
//                return cityDao.findByNameCursor(query);
//            } catch (Exception e) {
//                Timber.e(e);
//                return null;
//            }
//        }
//    });
//
//        result.addSource(cursorLiveData, data -> {
//        result.removeSource(cursorLiveData);
//
//        if (data.getCount() == 0) {
//            LiveData<ApiResponse<CityResponse>> response = worldWeatherService.getCity(query, Constants.QUERY_PARAM_SHOW_LOCALTIME_VALUE_YES, Constants.QUERY_PARAM_FORMAT);
//
//            result.addSource(response, apiResponse -> {
//                result.removeSource(response);
//                if (apiResponse instanceof ApiResponse.ApiSuccessResponse) {
//                    appExecutors.diskIO().execute(() -> {
//                        CityResponse body = ((ApiResponse.ApiSuccessResponse<CityResponse>) apiResponse).getBody();
//                        if (body != null) {
//                            if (body.getSearchApi() != null) {
//                                List<CityEntity> cities = cityConverter.toEntities(body.getSearchApi().getResult());
//                                cityDao.insertAll(cities);
//                            }
//                            appExecutors.ui().execute(() -> {
//                                result.addSource(cursorLiveData, cursor -> result.postValue(Resource.success(cursor)));
//                            });
//                        }
//                    });
//                } else {
//                    ApiResponse.ApiErrorResponse<?> errorResponse = (ApiResponse.ApiErrorResponse<?>) apiResponse;
//                    result.addSource(cursorLiveData, new Observer<Cursor>() {
//                        @Override
//                        public void onChanged(Cursor cursor) {
//                            result.postValue(Resource.error(errorResponse.getErrorMessage(), data, errorResponse.getException()));
//                        }
//                    });
//                }
//            });
//        }
//        result.postValue(Resource.success(data));
//    });
}
