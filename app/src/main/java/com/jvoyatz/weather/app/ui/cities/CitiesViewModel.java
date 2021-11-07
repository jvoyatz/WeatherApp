package com.jvoyatz.weather.app.ui.cities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.repository.CityRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CitiesViewModel extends ViewModel {
    private final CityRepository cityRepository;
    private final MutableLiveData<Boolean> triggerRefreshFavoriteCities;
    public MutableLiveData<CityEntity> currentCityLiveData;

    @Inject
    public CitiesViewModel(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
        triggerRefreshFavoriteCities = new MutableLiveData<>();
    }

    /**
     * Returns a livedata instance containing a list of CityEntity objects
     */
    public LiveData<List<CityEntity>> getFavoritesCitiesLiveData(){
        return Transformations.switchMap(triggerRefreshFavoriteCities, input -> cityRepository.getFavoriteCities());
    }

    /**
     * Sets the value of triggerRefreshFavoriteCities equals to true, so as to trigger the switchMap
     * used in the getFavoritesCitiesLiveData() method
     */
    public void refreshFavoriteCitiesList(){
        triggerRefreshFavoriteCities.postValue(true);
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
}