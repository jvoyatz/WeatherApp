package com.jvoyatz.weather.app.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jvoyatz.weather.app.models.converters.weather.WeatherTypeListConverter
import com.jvoyatz.weather.app.models.entities.CityEntity
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity

@Database(entities = [CityEntity::class, WeatherEntity::class], version = 1)
@TypeConverters(WeatherTypeListConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun cityDAO(): CityDao
    abstract fun weatherDAO(): WeatherDao
}