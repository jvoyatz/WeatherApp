package com.jvoyatz.weather.app.storage.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.jvoyatz.weather.app.models.api.entities.CityEntity;

@Database(entities = {CityEntity.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract CityDao cityDAO();
}