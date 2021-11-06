package com.jvoyatz.weather.app.ui.cities;

import android.text.TextUtils;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.repository.CityRepository;
import com.jvoyatz.weather.app.util.AbsentLiveData;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CitiesViewModel extends ViewModel {

    private final CityRepository cityRepository;

    private final MutableLiveData<String> triggerSearchCityLiveData;

    @Inject
    public CitiesViewModel(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
        triggerSearchCityLiveData = new MutableLiveData<>();
    }

    /**
     * Called when user selects to search the written name in the search box
     * @param query String, the city, region or area name
     */
    public void triggerSearch(String query){
        triggerSearchCityLiveData.postValue(query);
    }

    /**
     * {@link Transformations#switchMap(LiveData, Function)} is being used to trigger the invoking of the
     * {@link CityRepository#searchCity(String)} from {@link CityRepository}.
     * This happens if the triggerSearchCityLiveData has a non-null value
     *
     * @return Livedata, returns the Livedata containing the results regarding user search
     */
    public LiveData<Resource<List<CityEntity>>> getSearchLiveData(){
        return Transformations.switchMap(triggerSearchCityLiveData, new Function<String, LiveData<Resource<List<CityEntity>>>>() {
            @Override
            public LiveData<Resource<List<CityEntity>>> apply(String input) {
                if(!TextUtils.isEmpty(input)){
                   // return cityRepository.searchCity(input);
                }
                return AbsentLiveData.create();
            }
        });
    }
}