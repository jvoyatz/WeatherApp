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
import com.jvoyatz.weather.app.repository.CityRepository;
import com.jvoyatz.weather.app.util.AbsentLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlin.Triple;

/**
 * Viewmodel used in WeatherActivity
 */
@HiltViewModel
public class WeatherViewModel extends ViewModel {

    private final CityRepository cityRepository;
    private final MutableLiveData<String> citySearchQueryLiveData;
    private final MutableLiveData<Triple<String, String, String>> favoriteCityTriggerLiveData;

    @Inject
    public WeatherViewModel(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
        citySearchQueryLiveData = new MutableLiveData<>();
        favoriteCityTriggerLiveData = new MutableLiveData<>();
    }

    /**
     * Returns cities matching the given query
     * @return Cursor object wrapped in LiveData
     */
    public LiveData<Resource<Cursor>> getCitiesSuggestions(){
        return Transformations.switchMap(citySearchQueryLiveData, new Function<String, LiveData<Resource<Cursor>>>() {
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
        citySearchQueryLiveData.postValue(query);
    }

    /**
     * Called when users click on the add icon being shown in the search results
     * String (name, region, country) params to find city through a query
     */
    public void markCityAsFavorite(@NonNull String cityName, @NonNull String region, @NonNull String country){
        favoriteCityTriggerLiveData.postValue(new Triple<>(cityName, region, country));
    }

    /**
     * favoritizeCityLiveData is used as a trigger to execute the insert task
     * for a favorite city.
     *
     * Returns livedata holding a boolean to know whether the task finished correctly.
     *
     */
    public LiveData<Pair<Boolean, Boolean>> getFavoriteCityResultLiveData() {
        return Transformations.switchMap(favoriteCityTriggerLiveData, new Function<Triple<String, String, String>, LiveData<Pair<Boolean, Boolean>>>() {
            @Override
            public LiveData<Pair<Boolean, Boolean>> apply(Triple<String, String, String> input) {
                if(input != null){
                    return cityRepository.updateFavoriteCity(input.component1(), input.component2(), input.component3());
                }
                return AbsentLiveData.create();
            }
        });
    }
}
