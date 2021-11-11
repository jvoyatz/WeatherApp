package com.jvoyatz.weather.app.ui.home.details;

import android.text.TextUtils;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayAstronomyEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayHourEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity;
import com.jvoyatz.weather.app.repository.WeatherRepository;
import com.jvoyatz.weather.app.ui.home.details.pager.WeatherHourItemsAdapter;
import com.jvoyatz.weather.app.util.AbsentLiveData;
import com.jvoyatz.weather.app.util.Objects;
import com.jvoyatz.weather.app.util.Utils;

import java.io.Writer;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import timber.log.Timber;

@HiltViewModel
public class WeatherDetailsViewModel extends ViewModel {

    private final AppExecutors appExecutors;
    private final WeatherRepository weatherRepository;
    public LiveData<Resource<WeatherEntity>> weatherEntityLiveData;
    public LiveData<WeatherDayEntity> weatherCurrentDayEntityLiveData;

    @Inject
    public WeatherDetailsViewModel(AppExecutors appExecutors, WeatherRepository weatherRepository) {
        this.appExecutors = appExecutors;
        this.weatherRepository = weatherRepository;
    }

//    private LiveData<Resource<WeatherEntity>> getWeatherEntityLiveData() {
//        if(weatherEntityLiveData == null){
//            throw new IllegalStateException("not initialized livedata");
//        }
//        return weatherEntityLiveData;
//    }

    /**
     * Takes as argument the Livedata instance provided by the {@link com.jvoyatz.weather.app.WeatherViewModel}
     * We use this livedata to fill our UI with data.
     * This happens when user select to see the details for the current day.
     * However there is a case where user wants to see details for future days, in that case
     * we make an explicit request to request data for that particular date.
     */
    public LiveData<Resource<WeatherEntity>> getWeatherEntityLiveData(LiveData<Resource<WeatherEntity>> weatherEntityLiveData, int dayIndex) {

        if(dayIndex == 0) {
//            this.weatherEntityLiveData = Transformations.map(weatherEntityLiveData, input -> {
//                if (input != null) {
//                    return input.data;
//                }
//                return null;
//            });
            this.weatherEntityLiveData = weatherEntityLiveData;
        }else{
            this.weatherEntityLiveData = Transformations.switchMap(weatherEntityLiveData, new Function<Resource<WeatherEntity>, LiveData<Resource<WeatherEntity>>>() {
                @Override
                public LiveData<Resource<WeatherEntity>> apply(Resource<WeatherEntity> input) {
                    if (input != null) {
                        final WeatherEntity data = input.data;
                        if (data != null) {
                            final WeatherDayEntity weatherDayEntity = data.getWeather().get(dayIndex);
                            final String date = weatherDayEntity.getDate();

                            StringBuilder query = new StringBuilder();
                            if (!TextUtils.isEmpty(data.getCity())) {
                                query.append(data.getCity());
                                if (!TextUtils.isEmpty(data.getRegion())) {
                                    query.append(", ").append(data.getRegion());
                                }
                                if (!TextUtils.isEmpty(data.getCountry())) {
                                    query.append(", ").append(data.getCountry());
                                }
                            }
                            return weatherRepository.getCityWeatherForecastForDate(query.toString(), date);
                        }
                    }
                    return AbsentLiveData.create();
                }
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
            weatherCurrentDayEntityLiveData = Transformations.switchMap(weatherEntityLiveData, new Function<Resource<WeatherEntity>, LiveData<WeatherDayEntity>>() {
                @Override
                public LiveData<WeatherDayEntity> apply(Resource<WeatherEntity> input) {
                    if(input != null && input.data != null){
                        return new LiveData<WeatherDayEntity>() {
                            @Override
                            protected void onActive() {
                                super.onActive();
                                if(!Objects.isEmpty(input.data.getWeather())){
                                    postValue(input.data.getWeather().get(0));
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

    /**
     * Livedata which returns a list of all days quick forecast along with
     * the forecast for every {@link WeatherDayHourEntity} entity contained in the list
     *
     * This list contains parses all the provided data and adapts except the WeatherDayHourEntity items,
     * {@link WeatherDayEntity}  and {@link WeatherDayAstronomyEntity} instances to {@link WeatherDayHourEntity}
     * This happens in order to be supported into the ListAdapter shown by the invoking Fragment.
     *
     * Our Adapter can have multiple types inside itself.
     *
     * The logic of fetching data is
     * 1) to exclude all items which have a date before the current
     * 2) not adding a header item for the current day
     * 3) add {@link WeatherDayHourEntity} objects to the list
     * 4) and trying to find the closest near to the provided sunshine and sunset time for each, and add this values as WeatherDayAstronomyEntity
     *    in the appropriate index of the list.
     */
    public LiveData<List<WeatherDayHourEntity>> getWeatherDayHourEntityLiveData() {
        return Transformations.switchMap(weatherEntityLiveData, new Function<Resource<WeatherEntity>, LiveData<List<WeatherDayHourEntity>>>() {
            @Override
            public LiveData<List<WeatherDayHourEntity>> apply(Resource<WeatherEntity> resource) {
                if (resource != null && resource.data != null &&  !Objects.isEmpty(resource.data.getWeather())) {
                    return new LiveData<List<WeatherDayHourEntity>>() {
                        @Override
                        protected void onActive() {
                            super.onActive();

                            final Date nowDate = Calendar.getInstance().getTime();
                            final WeatherEntity data = resource.data;

                            appExecutors.diskIO().execute(() -> {

                                List<WeatherDayHourEntity> hours = new ArrayList<>();

                                for (int i = 0; i < data.getWeather().size(); i++) {
                                    String formalDate = "Not Provided Date";
                                    final WeatherDayEntity day = data.getWeather().get(i);

                                    //if no items, then forward to the next day
                                    if(Objects.isEmpty(day.getHourly()))
                                        continue;

                                    ///////////////////////////////////////////////////////////////////////////////////////////
                                    //filters the list of hourly field so as to remove items before the current date
                                    final List<WeatherDayHourEntity> filteredHours = new ArrayList<>(day.getHourly().size());
                                    for (WeatherDayHourEntity hour : day.getHourly()) {
                                        try {
                                            final Date date = Utils.getFullDateFormatter().parse(hour.getTime());
                                            if (date == null || date.compareTo(nowDate) < 0)
                                                continue;
                                            filteredHours.add(hour);
                                        } catch (ParseException e) {
                                            Timber.e(e);
                                        }
                                    }
                                    day.setHourly(filteredHours);
                                    ////////////////////////////////////////////////////////////////////////////////////////////

                                    //parsing day date
                                    final Date date = Utils.parseYYYYMMddStr(day.getDate());
                                    if (date != null) {
                                        formalDate = Utils.formatFormalDate(date);
                                    }

                                    //adding it as a header
                                    //except the current day, the first found in the list
                                    if (i != 0) {
                                        hours.add(WeatherDayHourEntity.builder()
                                                .withWeatherCode(WeatherHourItemsAdapter.DAY_STR)
                                                .withTime(formalDate)
                                                .build());
                                    }
                                    //adds the weather day hour items
                                    hours.addAll(day.getHourly());

                                    //find sunrise and sunset between given hours for each day
                                    if (!Objects.isEmpty(day.getAstronomy())) {
                                        final WeatherDayAstronomyEntity astronomy = day.getAstronomy().get(0);
                                        //07:04 AM format
                                        try {
                                            //get sunshine date
                                            WeatherDayHourEntity closeToSunriseEntity;
                                            final Date yMdDate = Utils.parseYYYYMMddStr(day.getDate());
                                            Date sunriseDate = Utils.mergeHHmmDates(yMdDate, astronomy.getSunrise());
                                            if (sunriseDate != null && sunriseDate.compareTo(nowDate) >= 0) {
                                                //get the closest date from weather hour items compared to sunshine
                                                closeToSunriseEntity = Utils.getClosestWeatherDayHourEntity(hours, sunriseDate.getTime());
                                                if (closeToSunriseEntity != null) {
                                                    addItem(hours, closeToSunriseEntity, sunriseDate, Utils.get24TimeFrom12(astronomy.getSunrise()), WeatherHourItemsAdapter.DAY_SUNRISE);
                                                }
                                            }
                                            //get sunset date
                                            Date sunsetDate = Utils.mergeAmDate(yMdDate, astronomy.getSunset());
                                            WeatherDayHourEntity closeToSunsetEntity;
                                            if (sunsetDate != null && sunsetDate.compareTo(nowDate) < 0) {
                                                //get the closet date from weather hour items compared to sunset
                                                closeToSunsetEntity = Utils.getClosestWeatherDayHourEntity(hours, sunsetDate.getTime());
                                                if (closeToSunsetEntity != null) {
                                                    addItem(hours, closeToSunsetEntity, sunsetDate, Utils.get24TimeFrom12(astronomy.getSunset()), WeatherHourItemsAdapter.DAY_SUNSET);
                                                }
                                            }
                                        } catch (Exception e) {
                                            Timber.e(e);
                                        }
                                    }
                                }
                                //notify observer
                                postValue(hours);
                            });
                        }
                    };
                }
                return AbsentLiveData.create();
            }
        });
    }

    /**
     * finds the index of entity param in the hours list
     * then depending the result of compare between the newDate and the entity's,
     * adds a new element right before or after the index of the found entity
     */
    private void addItem(List<WeatherDayHourEntity> hours, WeatherDayHourEntity entity, Date newDate, String data, String weatherCode){
        //finds its index
        final int index = hours.indexOf(entity);
        if (index != -1) {//if equals -1, then no items?
            final WeatherDayHourEntity weatherDayHourEntity = hours.get(index);
            final Date closeDate = Utils.parseFullStr(weatherDayHourEntity.getTime());
            if (closeDate != null) {
                if (closeDate.compareTo(newDate) < 0) {
                    hours.add(index + 1, WeatherDayHourEntity.builder()
                            .withTime(data)
                            .withWeatherCode(weatherCode)
                            .build());
                } else {
                    hours.add(index, WeatherDayHourEntity.builder()
                            .withTime(data)
                            .withWeatherCode(weatherCode)
                            .build());
                }
            }
        }
    }
}