package com.jvoyatz.weather.app;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity;
import com.jvoyatz.weather.app.repository.CityRepository;
import com.jvoyatz.weather.app.repository.WeatherRepository;
import com.jvoyatz.weather.app.util.AbsentLiveData;
import com.jvoyatz.weather.app.util.Event;
import com.jvoyatz.weather.app.util.LocationLiveData;
import com.jvoyatz.weather.app.util.PairSourcesLiveData;

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
    private LocationLiveData locationLiveData;
    private final MutableLiveData<String> searchCityLiveData;
    private MutableLiveData<CityEntity> selectedCityEntityLiveData;
    private final MutableLiveData<Event<Triple<String, String, String>>> favoriteCityLiveData;
    private LiveData<Resource<WeatherEntity>> weatherEntityLiveData;
    private final MutableLiveData<Event<Boolean>> triggerRefreshFavoriteCities;

    @Inject
    public WeatherViewModel(@NonNull Application application, CityRepository cityRepository, WeatherRepository weatherRepository) {
        super(application);
        this.cityRepository = cityRepository;
        this.weatherRepository = weatherRepository;
        searchCityLiveData = new MutableLiveData<>();
        favoriteCityLiveData = new MutableLiveData<>();
        triggerRefreshFavoriteCities = new MutableLiveData<>();
        locationLiveData = new LocationLiveData(getApplication().getApplicationContext());
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
            final PairSourcesLiveData<CityEntity, Resource<Location>> twoSourcesLiveData = new PairSourcesLiveData<CityEntity, Resource<Location>>
                    (getSelectedCityEntityLiveData(), locationLiveData);
            weatherEntityLiveData = Transformations.switchMap(twoSourcesLiveData, new Function<Pair<CityEntity, Resource<Location>>, LiveData<Resource<WeatherEntity>>>() {
                @Override
                public LiveData<Resource<WeatherEntity>> apply(Pair<CityEntity, Resource<Location>> input) {
                    if (input != null && input.first != null) {
                        return weatherRepository.getCityWeatherForecast(input.first);
                    }else if(input != null && input.second != null && input.second.data != null){
                        return weatherRepository.getWeatherForecastLatLon(input.second.data.getLatitude(), input.second.data.getLongitude());
                    }
                    return AbsentLiveData.create();
                }
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
     * start listening for location updates
     */
    public void startListeningLocationUpdates(){
        locationLiveData.start();
    }
}
