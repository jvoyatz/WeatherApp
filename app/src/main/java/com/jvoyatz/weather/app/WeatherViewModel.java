package com.jvoyatz.weather.app;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
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

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlin.Triple;
import timber.log.Timber;

/**
 * Viewmodel used in WeatherActivity
 */
@HiltViewModel
public class WeatherViewModel extends ViewModel {

    private final CityRepository cityRepository;
    private final WeatherRepository weatherRepository;
    private final MutableLiveData<String> searchCityLiveData;
    private MutableLiveData<CityEntity> selectedCityEntityLiveData;
    private final MutableLiveData<Triple<String, String, String>> favoriteCityLiveData;
    private LiveData<Resource<WeatherEntity>> weatherEntityLiveData;

    @Inject
    public WeatherViewModel(CityRepository cityRepository, WeatherRepository weatherRepository) {
        this.cityRepository = cityRepository;
        this.weatherRepository = weatherRepository;
        searchCityLiveData = new MutableLiveData<>();
        favoriteCityLiveData = new MutableLiveData<>();
    }

    /**
     * Returns cities matching the given query
     * @return Cursor object wrapped in LiveData
     */
    public LiveData<Resource<Cursor>> getCitiesSuggestions(){
        return Transformations.switchMap(searchCityLiveData, new Function<String, LiveData<Resource<Cursor>>>() {
            @Override
            public LiveData<Resource<Cursor>> apply(String input) {
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
        favoriteCityLiveData.postValue(new Triple<>(cityName, region, country));
    }

    /**
     * Returns livedata holding a boolean to know whether the task finished correctly.
     *
     * The task of updating city in the db is being triggered when user selects a
     * from the suggestions list.
     */
    public LiveData<Pair<Boolean, Boolean>> getFavoriteCityResultLiveData() {
        return Transformations.switchMap(favoriteCityLiveData, new Function<Triple<String, String, String>, LiveData<Pair<Boolean, Boolean>>>() {
            @Override
            public LiveData<Pair<Boolean, Boolean>> apply(Triple<String, String, String> input) {
                if(input != null){
                    return cityRepository.updateFavoriteCity(input.component1(), input.component2(), input.component3());
                }
                return AbsentLiveData.create();
            }
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
            weatherEntityLiveData = Transformations.switchMap(getSelectedCityEntityLiveData(), new Function<CityEntity, LiveData<Resource<WeatherEntity>>>() {
                @Override
                public LiveData<Resource<WeatherEntity>> apply(CityEntity input) {
                    Timber.d("apply() called with: input = [" + input + "]");
                    if (input != null) {
                        return weatherRepository.getCityWeatherForecast(input);
                    }
                    return AbsentLiveData.create();
                }
            });
        }
        return weatherEntityLiveData;
    }

}
