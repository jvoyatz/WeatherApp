package com.jvoyatz.weather.app.storage.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity

/**
 * Inteface for accessing database of City data
 */
@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM WeatherEntity WHERE " +
            "city LIKE '%' || :city || '%' AND " +
            "region LIKE '%' :region '%' AND " +
            "country LIKE '%' || :country || '%' limit 1")
    fun getWeatherEntity(city: String, region: String, country:String):LiveData<WeatherEntity>

    @Query("SELECT * FROM WeatherEntity")
    fun getWeatherEntities():List<WeatherEntity>
}