package com.jvoyatz.weather.app.ui.home.details.pager;

import android.view.View;

import com.jvoyatz.weather.app.models.entities.weather.WeatherDayHourEntity;

public interface WeatherHourItemsHandler {
    void onViewClicked(View view, WeatherDayHourEntity entity);
}
