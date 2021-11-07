package com.jvoyatz.weather.app.ui.cities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jvoyatz.weather.app.models.entities.CityEntity;

public interface CitiesHandler {
    void onViewClicked(@NonNull CityEntity item, @Nullable CityEntity selectedItem);
    void onFavoriteIconClick(@NonNull CityEntity item);
}
