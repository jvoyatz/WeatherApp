package com.jvoyatz.weather.app.ui.cities;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.repository.CityRepository;
import com.jvoyatz.weather.app.util.AbsentLiveData;
import com.jvoyatz.weather.app.util.Event;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CitiesViewModel extends ViewModel {

    private final CityRepository cityRepository;
    private MutableLiveData<Event<Boolean>> triggerRefreshFavoriteCities;
    public MutableLiveData<CityEntity> currentCityLiveData;

    @Inject
    public CitiesViewModel(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /**
     * Returns a livedata instance containing a list of CityEntity objects
     */
    public LiveData<List<CityEntity>> getFavoritesCitiesLiveData(){
        return Transformations.switchMap(triggerRefreshFavoriteCities, new Function<Event<Boolean>, LiveData<List<CityEntity>>>() {
            @Override
            public LiveData<List<CityEntity>> apply(Event<Boolean> event) {
                if(event != null){
                    final Boolean data = event.getContentIfNotHandled();
                    if(data != null){
                        return cityRepository.getFavoriteCities();
                    }
                }
                return AbsentLiveData.create();
            }
        });
    }

    /**
     * Sets the value of triggerRefreshFavoriteCities equals to true, so as to trigger the switchMap
     * used in the getFavoritesCitiesLiveData() method
     */
    public void refreshFavoriteCitiesList(){
        triggerRefreshFavoriteCities.postValue(new Event<>(true));
    }

    /**
     * Sets the currentCityLiveData field.
     * @param currentCityLiveData the already initiated livedata instance
     */
    public void setCurrentCitySelectedLiveData(MutableLiveData<CityEntity> currentCityLiveData) {
        this.currentCityLiveData = currentCityLiveData;
    }

    /**
     * Returns the livedata holder holding the value of the selected city
     * at the moment.
     */
    public MutableLiveData<CityEntity> getCurrentCitySelectedLiveData() {
        if(currentCityLiveData == null){
            currentCityLiveData = new MutableLiveData<>();
        }
        return currentCityLiveData;
    }

    /**
     * Invoked by the WeatherViewModel, when CityFragment is created
     * Activity sets a value to this LiveData when a certain event like selecting
     * as favorite or the opposite has happened
     *
     * @param triggerRefreshFavoriteCities the Activity's livedata
     */
    public void setTriggerRefreshFavoriteCities(MutableLiveData<Event<Boolean>> triggerRefreshFavoriteCities) {
        this.triggerRefreshFavoriteCities = triggerRefreshFavoriteCities;
    }
}