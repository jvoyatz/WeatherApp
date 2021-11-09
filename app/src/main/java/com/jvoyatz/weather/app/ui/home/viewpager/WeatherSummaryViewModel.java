package com.jvoyatz.weather.app.ui.home.viewpager;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.api.weather.Weather;
import com.jvoyatz.weather.app.models.api.weather.CurrentCondition;
import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.repository.WeatherRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WeatherSummaryViewModel extends ViewModel {

    public MutableLiveData<CurrentCondition> weatherCurrentConditionLiveData;
    public MutableLiveData<CityEntity> currentCityLiveData;
    private final WeatherRepository weatherRepository;

    @Inject
    public WeatherSummaryViewModel(WeatherRepository weatherRepository) {

        this.weatherRepository = weatherRepository;
        weatherCurrentConditionLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<CurrentCondition> getWeatherCurrentConditionLiveData(LiveData<Resource<Weather>> weatherResponseLiveData) {
        weatherCurrentConditionLiveData = (MutableLiveData<CurrentCondition>) Transformations.map(weatherResponseLiveData,
                new Function<Resource<Weather>, CurrentCondition>() {
            @Override
            public CurrentCondition apply(Resource<Weather> input) {
                if(input != null){
                    switch (input.status){
                        case ERROR:
                            break;
                        case SUCCESS:
                            break;
                    }
                }
                return null;
            }
        });

        return weatherCurrentConditionLiveData;
    }
}
