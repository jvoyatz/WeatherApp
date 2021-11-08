package com.jvoyatz.weather.app.storage.db;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity;

import java.util.List;

/**
 * Inteface for accessing database of City data
 */
@Dao
public interface WeatherDao {
    @Insert(onConflict = REPLACE)
    void insert(@NonNull WeatherEntity weatherEntity);

    @Query("SELECT * FROM WeatherEntity WHERE city LIKE '%' || :city || '%' AND  region LIKE '%' ||  :region || '%' AND country LIKE '%' || :country || '%' limit 1")
    LiveData<WeatherEntity> getWeatherEntity(@NonNull String city, String region, @NonNull String country);

    @Query("SELECT * FROM WeatherEntity")
    List<WeatherEntity> getWeatherEntities();
}
