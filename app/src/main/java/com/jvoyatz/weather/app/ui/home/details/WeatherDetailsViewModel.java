package com.jvoyatz.weather.app.ui.home.details;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity;
import com.jvoyatz.weather.app.util.AbsentLiveData;
import com.jvoyatz.weather.app.util.AbsentObserver;
import com.jvoyatz.weather.app.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WeatherDetailsViewModel extends ViewModel {
    private final AppExecutors appExecutors;
    public LiveData<WeatherEntity> weatherEntityLiveData;
    public LiveData<WeatherDayEntity> weatherCurrentDayEntityLiveData;

    @Inject
    public WeatherDetailsViewModel(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    /**
     * Takes as argument the Livedata instance provided in the {@link com.jvoyatz.weather.app.WeatherViewModel}
     * and maps it into WeatherEntity.
     */
    public LiveData<WeatherEntity> getWeatherEntityLiveData(LiveData<Resource<WeatherEntity>> weatherEntityLiveData) {
        if(this.weatherEntityLiveData == null){
            this.weatherEntityLiveData = Transformations.map(weatherEntityLiveData, input -> {
                if(input != null) {
                    return input.data;
                }
                return null;
            });
        }
        return this.weatherEntityLiveData;
    }

    /**
     * Switches the weatherEntityLiveData, to return a livedata instance holding
     * an object of {@link WeatherDayEntity}
     *
     */
    public LiveData<WeatherDayEntity> getWeatherCurrentDayEntityLiveData() {
        if(weatherCurrentDayEntityLiveData == null){
            weatherCurrentDayEntityLiveData = Transformations.switchMap(weatherEntityLiveData, new Function<WeatherEntity, LiveData<WeatherDayEntity>>() {
                @Override
                public LiveData<WeatherDayEntity> apply(WeatherEntity input) {
                    if(input != null){
                        return new LiveData<WeatherDayEntity>() {
                            @Override
                            protected void onActive() {
                                super.onActive();
                                if(!Objects.isEmpty(input.getWeather())){
                                    postValue(input.getWeather().get(0));
                                }
                            }
                        };
                    }
                    return AbsentLiveData.create();
                }
            });
        }
        return weatherCurrentDayEntityLiveData;
    }
}