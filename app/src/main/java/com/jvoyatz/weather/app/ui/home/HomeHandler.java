package com.jvoyatz.weather.app.ui.home;

import android.view.View;

import androidx.annotation.NonNull;

import com.jvoyatz.weather.app.models.entities.weather.WeatherDayEntity;

/**
 * Handler implemented by HomeFragment to support clicks on items
 * inside the RecyclerView
 */
public interface HomeHandler {
    void onViewClicked(View view, int position);
}
