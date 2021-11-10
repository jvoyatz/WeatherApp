package com.jvoyatz.weather.app.ui.home.details.pager;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayAstronomyEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayHourEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity;
import com.jvoyatz.weather.app.util.AbsentLiveData;
import com.jvoyatz.weather.app.util.Objects;
import com.jvoyatz.weather.app.util.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import timber.log.Timber;

/**
 * A screen which shows to the user a list of
 * hourly weather items
 */
@HiltViewModel
public class WeatherDetailsHourlyListViewModel extends ViewModel {

    private final AppExecutors appExecutors;
    public LiveData<WeatherEntity> weatherEntityLiveData;
    public LiveData<WeatherDayEntity> weatherCurrentDayEntityLiveData;

    @Inject
    public WeatherDetailsHourlyListViewModel(AppExecutors appExecutors) {
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
     *
     */
    public LiveData<List<WeatherDayHourEntity>> getWeatherDayHourEntityLiveData(LiveData<Resource<WeatherEntity>> weatherEntityLiveData) {
        return Transformations.switchMap(getWeatherEntityLiveData(weatherEntityLiveData), new Function<WeatherEntity, LiveData<List<WeatherDayHourEntity>>>() {
            @Override
            public LiveData<List<WeatherDayHourEntity>> apply(WeatherEntity input) {
                if (input != null && !Objects.isEmpty(input.getWeather())) {
                    return new LiveData<List<WeatherDayHourEntity>>() {
                        @Override
                        protected void onActive() {
                            super.onActive();
                            final Date nowDate = Calendar.getInstance().getTime();

                            appExecutors.diskIO().execute(() -> {
                                List<WeatherDayHourEntity> hours = new ArrayList<>();
                                for (int i = 0; i < input.getWeather().size(); i++) {
                                    String formalDate = "Not Provided Date";
                                    final WeatherDayEntity day = input.getWeather().get(i);

                                    ///////////////////////////////////////////////////////////////////////////////////////////
                                    //filters the list of hourly field so as to remove items before
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

                                            if (sunriseDate != null) {
                                                //get the closest date from weather hour items compared to sunshine
                                                closeToSunriseEntity = Utils.getClosestWeatherDayHourEntity(hours, sunriseDate.getTime());
                                                if (closeToSunriseEntity != null) {

                                                    //finds its index
                                                    final int index = hours.indexOf(closeToSunriseEntity);
                                                    if (index != -1) {//if equals -1, then no items?

                                                        final WeatherDayHourEntity weatherDayHourEntity = hours.get(index);
                                                        final Date closeDate = Utils.parseFullStr(weatherDayHourEntity.getTime());

                                                        if (closeDate != null) {
                                                            if (closeDate.compareTo(sunriseDate) < 0) {
                                                                hours.add(index + 1, WeatherDayHourEntity.builder()
                                                                        .withTime(Utils.get24TimeFrom12(astronomy.getSunrise()))
                                                                        .withWeatherCode(WeatherHourItemsAdapter.DAY_SUNRISE)
                                                                        .build());
                                                            } else {
                                                                hours.add(index, WeatherDayHourEntity.builder()
                                                                        .withTime(Utils.get24TimeFrom12(astronomy.getSunrise()))
                                                                        .withWeatherCode(WeatherHourItemsAdapter.DAY_SUNRISE)
                                                                        .build());
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            //get sunset date
                                            Date sunsetDate = Utils.mergeAmDate(yMdDate, astronomy.getSunset());
                                            WeatherDayHourEntity closeToSunsetEntity;

                                            if (sunsetDate != null) {

                                                //get the closet date from weather hour items compared to sunset
                                                closeToSunsetEntity = Utils.getClosestWeatherDayHourEntity(hours, sunsetDate.getTime());

                                                if (closeToSunsetEntity != null) {

                                                    final int index = hours.indexOf(closeToSunsetEntity);
                                                    if (index != -1) {
                                                        final WeatherDayHourEntity weatherDayHourEntity = hours.get(index);
                                                        final Date closeDate = Utils.parseFullStr(weatherDayHourEntity.getTime());
                                                        if (closeDate != null) {
                                                            final int compare = closeDate.compareTo(sunsetDate);
                                                            if (compare < 0) {
                                                                hours.add(index + 1, WeatherDayHourEntity.builder()
                                                                        .withTime(Utils.get24TimeFrom12(astronomy.getSunset()))
                                                                        .withWeatherCode(WeatherHourItemsAdapter.DAY_SUNSET)
                                                                        .build());
                                                            } else {
                                                                hours.add(index, WeatherDayHourEntity.builder()
                                                                        .withTime(Utils.get24TimeFrom12(astronomy.getSunset()))
                                                                        .withWeatherCode(WeatherHourItemsAdapter.DAY_SUNSET)
                                                                        .build());
                                                            }
                                                        }
                                                    }
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
}