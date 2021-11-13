package com.jvoyatz.weather.app;

import android.app.Application;
import android.database.Cursor;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.Status;
import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity;
import com.jvoyatz.weather.app.repository.CityRepository;
import com.jvoyatz.weather.app.repository.WeatherRepository;
import com.jvoyatz.weather.app.util.AbsentLiveData;
import com.jvoyatz.weather.app.util.Event;
import com.jvoyatz.weather.app.util.LocationLiveData;
import com.jvoyatz.weather.app.util.PairSourcesLiveData;
import com.jvoyatz.weather.app.util.TripleSourcesLiveData;
import com.jvoyatz.weather.app.util.Utils;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlin.Triple;
import timber.log.Timber;

/**
 * Viewmodel used in WeatherActivity
 */
@HiltViewModel
public class WeatherViewModel extends AndroidViewModel {

    private final CityRepository cityRepository;
    private final WeatherRepository weatherRepository;
    private final AppExecutors appExecutors;
    private LocationLiveData locationLiveData;
    private final MutableLiveData<String> searchCityLiveData;
    private MutableLiveData<CityEntity> selectedCityEntityLiveData;
    //used to show information in layouts for cities
    public MutableLiveData<CityEntity> currentCityEntityLiveData;
    private MutableLiveData<Event<Triple<String, String, String>>> nonFavoriteCityLiveData;
    private final MutableLiveData<Event<Triple<String, String, String>>> favoriteCityLiveData;
    private LiveData<Resource<WeatherEntity>> weatherEntityLiveData;
    private final MutableLiveData<Event<Boolean>> triggerRefreshFavoriteCities;

    @Inject
    public WeatherViewModel(@NonNull Application application, CityRepository cityRepository, WeatherRepository weatherRepository, AppExecutors appExecutors) {
        super(application);
        this.cityRepository = cityRepository;
        this.weatherRepository = weatherRepository;
        this.appExecutors = appExecutors;
        searchCityLiveData = new MutableLiveData<>();
        favoriteCityLiveData = new MutableLiveData<>();
        triggerRefreshFavoriteCities = new MutableLiveData<>();
        locationLiveData = new LocationLiveData(getApplication().getApplicationContext());
        nonFavoriteCityLiveData = new MutableLiveData<>();
    }

    /**
     * Returns cities matching the given query
     * @return Cursor object wrapped in LiveData
     */
    public LiveData<Resource<Cursor>> getCitiesSuggestions(){
        return Transformations.switchMap(searchCityLiveData, new Function<String, LiveData<Resource<Cursor>>>() {
            @Override
            public LiveData<Resource<Cursor>> apply(String input) {
                Timber.d("getCitiesSuggestions() called with: input = [" + input + "]");
                if(!TextUtils.isEmpty(input)) {
                    return cityRepository.searchForCitySuggestions(input);
                }
                return AbsentLiveData.create();
            }
        });
    }

    /**
     * Called when user types anything in the SearchView
     * @param query user's input
     */
    public void searchForCitiesSuggestions(@NonNull String query){
        searchCityLiveData.postValue(query);
    }

    /**
     * Called when users click on the add icon being shown in the search results
     * String (name, region, country) params to find city through a query
     */
    public void updateFavoriteCity(@NonNull String cityName, @NonNull String region, @NonNull String country){
        Timber.d("updateFavoriteCity() called with: cityName = [" + cityName + "], region = [" + region + "], country = [" + country + "]");
        favoriteCityLiveData.postValue(new Event<>(new Triple<>(cityName, region, country)));
    }

    /**
     * Returns livedata holding a boolean to know whether the task finished correctly.
     *
     * The task of updating city in the db is being triggered when user selects a
     * from the suggestions list.
     */
    public LiveData<Pair<Boolean, Boolean>> getFavoriteCityResultLiveData() {
        return Transformations.switchMap(favoriteCityLiveData, event -> {
            if(event != null){
                final Triple<String, String, String> input = event.getContentIfNotHandled();
                if(input != null)
                    return cityRepository.updateFavoriteCity(input.component1(), input.component2(), input.component3());
            }
            return AbsentLiveData.create();
        });
    }

    /**
     * Sets the current city as selected by the user
     */
    public void setSelectedCityEntityLiveData(CityEntity cityEntity){
        selectedCityEntityLiveData.postValue(cityEntity);
        locationLiveData.trigger();
    }

    /**
     * Invoked when user selects a city from suggestions view without
     * marking it as favorite.
     */
    public void setNonFavoriteCity(@NonNull String cityName, @NonNull String region, @NonNull String country) {
        nonFavoriteCityLiveData.postValue(new Event<>((new Triple<>(cityName, region, country))));
    }

    /**
     * Returns the livedata holder holding the value of the selected city
     * at the moment.
     */
    public MutableLiveData<CityEntity> getSelectedCityEntityLiveData() {
        if(selectedCityEntityLiveData == null){
            selectedCityEntityLiveData = new MutableLiveData<>();
        }
        return selectedCityEntityLiveData;
    }

    /**
     * Returns the Weather Forecast inside a Livedata instance.
     * Weather forecast is being fetched when user's selects a city.
     */
    public LiveData<Resource<WeatherEntity>> getWeatherResponseLiveData(){
        if(weatherEntityLiveData == null) {
            final LiveData<Resource<Location>> locationLiveData = getLocationLiveData();
//            final PairSourcesLiveData<CityEntity, Resource<Location>> twoSourcesLiveData = new PairSourcesLiveData<CityEntity, Resource<Location>>
//                    (getSelectedCityEntityLiveData(), locationLiveData);
//            weatherEntityLiveData = Transformations.switchMap(twoSourcesLiveData, new Function<Pair<CityEntity, Resource<Location>>, LiveData<Resource<WeatherEntity>>>() {
//                @Override
//                public LiveData<Resource<WeatherEntity>> apply(Pair<CityEntity, Resource<Location>> input) {
//                    if (input != null && input.first != null) {
//                        return weatherRepository.getCityWeatherForecast(input.first);
//                    }else if(input != null && input.second != null && input.second.data != null){
//                        return weatherRepository.getWeatherForecastLatLon(input.second.data.getLatitude(), input.second.data.getLongitude());
//                    }
//                    return AbsentLiveData.create();
//                }
//            });

            final TripleSourcesLiveData<CityEntity, Resource<Location>, Event<Triple<String, String, String>>> threeSourcesLiveData =
                    new TripleSourcesLiveData<>(getSelectedCityEntityLiveData(), locationLiveData, nonFavoriteCityLiveData);
            weatherEntityLiveData = Transformations.switchMap(threeSourcesLiveData,
                    input -> {

                        if (input != null) {
                            if(input.getThird() != null && !input.getThird().isHandled()){
                                final Event<Triple<String, String, String>> event = input.getThird();
                                if(event != null) {
                                    final Triple<String, String, String> content = event.getContentIfNotHandled();
                                    if(content != null) {
                                        MediatorLiveData<Resource<WeatherEntity>> mediatorLiveData = new MediatorLiveData<>();
                                        final LiveData<CityEntity> cityFindLiveData = cityRepository.findCity(content.component1(), content.component2(), content.component3());
                                        mediatorLiveData.addSource(cityFindLiveData, cityEntity -> {
                                            mediatorLiveData.removeSource(cityFindLiveData);
                                            currentCityEntityLiveData.postValue(cityEntity);

                                            final LiveData<Resource<WeatherEntity>> cityWeatherForecast = weatherRepository.getCityWeatherForecast(cityEntity);
                                            mediatorLiveData.addSource(cityWeatherForecast, new Observer<Resource<WeatherEntity>>() {
                                                @Override
                                                public void onChanged(Resource<WeatherEntity> weatherEntityResource) {
                                                    Timber.d("getWeatherResponseLiveDataonChanged() called with: weatherEntityResource = [" + weatherEntityResource + "]");
                                                    if(weatherEntityResource == null || (weatherEntityResource.status == Status.SUCCESS || weatherEntityResource.status == Status.ERROR)){
                                                        mediatorLiveData.removeSource(cityWeatherForecast);
                                                    }
                                                    mediatorLiveData.postValue(weatherEntityResource);
                                                }
                                            });
                                        });

                                        return mediatorLiveData;
                                    }
                                }
                            } else if (input.getFirst() != null) {
                                return weatherRepository.getCityWeatherForecast(input.getFirst());
                            } else if (input.getSecond() != null && input.getSecond().data != null) {
                                return weatherRepository.getWeatherForecastLatLon(input.getSecond().data.getLatitude(), input.getSecond().data.getLongitude());
                            }
                            return AbsentLiveData.create();
                        }
                        return AbsentLiveData.create();
                    });
        }
        return weatherEntityLiveData;
    }

    /**
     * posts an Event wrapped value to triggerRefreshFavoriteCities instance.
     * This value is observed by Cities fragment, when users adds/removes a city to/from his list,
     * in order to refresh the content
     */
    public void setTriggerRefreshFavoriteCities(){
        triggerRefreshFavoriteCities.postValue(new Event<>(true));
    }

    /**
     * Observes the current Event wrapped value, posted when user interacted with the insertion/delection of a city
     */
    public MutableLiveData<Event<Boolean>> getTriggerRefreshFavoriteCities() {
        return triggerRefreshFavoriteCities;
    }

    /**
     * Returns the current location wrapped in a livedata instace
     */
    public LiveData<Resource<Location>> getLocationLiveData() {
        return locationLiveData;
    }

    /**
     *
     * Creates a new LiveData object using Transformations.switchMap.
     * The switchMap takes as source a PairSourcesLiveData of getSelectedCityEntityLiveData & getLocationLiveData
     * then in the apply method, we check whether a city is selected otherwise we check the value
     * dispatched by LocationLiveData. In this case we use the {@link android.location.Geocoder} to get info
     * regarding the current Location.
     *
     */
    public MutableLiveData<CityEntity> getCityEntityLiveData() {
        if(currentCityEntityLiveData == null) {
            final PairSourcesLiveData<CityEntity, Resource<Location>> twoSourcesLiveData = new PairSourcesLiveData<>(getSelectedCityEntityLiveData(), locationLiveData);
            final TripleSourcesLiveData<CityEntity, Resource<Location>, Event<Triple<String, String, String>>> tripleSourcesLiveData =
                    new TripleSourcesLiveData<>(getSelectedCityEntityLiveData(), locationLiveData, nonFavoriteCityLiveData);


            currentCityEntityLiveData = (MutableLiveData<CityEntity>)
                    Transformations.switchMap(twoSourcesLiveData, new Function<Pair<CityEntity, Resource<Location>>, LiveData<CityEntity>>() {
                        @Override
                        public LiveData<CityEntity> apply(Pair<CityEntity, Resource<Location>> input) {
                            Timber.d("apply() called with: input = [" + input + "]");
                            if (input != null) {
                                if (input.first != null) {
                                    return new LiveData<CityEntity>() {
                                        @Override
                                        protected void onActive() {
                                            super.onActive();
                                            postValue(input.first);
                                        }
                                    };
                                } else if (input.second != null && input.second.data != null) {
                                    return new LiveData<CityEntity>() {
                                        @Override
                                        protected void onActive() {
                                            super.onActive();
                                            appExecutors.networkIO().execute(new Runnable() {
                                                @Override
                                                public void run() {
                                                    final CityEntity entity = Utils.getCityEntityFromGeocoder(getApplication().getApplicationContext(), input.second.data.getLatitude(), input.second.data.getLongitude());
                                                    postValue(entity);
                                                }
                                            });

                                        }
                                    };
                                }
                            }
                            return AbsentLiveData.create();
                        }
                    });

//            currentCityEntityLiveData = (MutableLiveData<CityEntity>) Transformations.switchMap(tripleSourcesLiveData,
//                    new Function<Triple<CityEntity, Resource<Location>, Event<Triple<String, String, String>>>, LiveData<CityEntity>>() {
//                @Override
//                public LiveData<CityEntity> apply(Triple<CityEntity, Resource<Location>, Event<Triple<String, String, String>>> input) {
//                    if(input != null){
//                        Timber.d("apply: getCityEntityLiveData " + input);
//                        if(input.getThird() != null){ //priority 1 when user selects a city for a quick overview of the forecast
//                            final Event<Triple<String, String, String>> event = input.getThird();
//                            final Triple<String, String, String> content = event.getContentIfNotHandled();
//                            if(content != null){
//                                return cityRepository.findCity(content.component1(), content.component2(), content.component3());
//                            }
//                        }else if(input.component1() != null){ //else priority 2 the current selected city
//                            return new LiveData<CityEntity>() {
//                                @Override
//                                protected void onActive() {
//                                    super.onActive();
//                                    postValue(input.component1());
//                                }
//                            };
//                        }else if(input.getSecond() != null && input.getSecond().data != null){//else the location if available
//                            return new LiveData<CityEntity>() {
//                                        @Override
//                                        protected void onActive() {
//                                            super.onActive();
//                                            final CityEntity entity =
//                                                    Utils.getCityEntityFromGeocoder(getApplication().getApplicationContext(),
//                                                            input.getSecond().data.getLatitude(), input.getSecond().data.getLongitude());
//                                            postValue(entity);
//                                        }
//                                    };
//                        }
//                    }
//                    return AbsentLiveData.create();
//                }
//            });
        }

        return currentCityEntityLiveData;
    }

    /**
     * start listening for location updates
     */
    public void startListeningLocationUpdates(){
        locationLiveData.start();
    }

}
