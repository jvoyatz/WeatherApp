package com.jvoyatz.weather.app.storage.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jvoyatz.weather.app.models.entities.CityEntity


@Dao
interface CityDao {
    @Query("SELECT * FROM CityEntity")
    fun getAllList(): List<CityEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cities: List<CityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: CityEntity)

    @Query("SELECT * FROM CityEntity WHERE name LIKE '%' || :query || '%' OR  region LIKE '%' ||  :query || '%' OR country LIKE '%' || :query || '%'")
    fun findByNameCursor(query: String): Cursor

    @Query("SELECT * FROM CityEntity WHERE name LIKE '%' || :city || '%' AND  region LIKE '%' ||  :region || '%' AND country LIKE '%' || :country || '%' limit 1")
    fun findByMultipleCriteria(city: String, region: String, country: String): LiveData<CityEntity>?

    @Query("SELECT * FROM CityEntity WHERE name LIKE '%' || :city || '%' AND  region LIKE '%' ||  :region || '%' AND country LIKE '%' || :country || '%' limit 1")
    fun findByMultipleCriteriaLimit1(city: String, region: String, country: String): LiveData<CityEntity?>?

    @Query("SELECT * FROM CityEntity WHERE isFavorite = 1")
    fun getFavoriteCities(): List<CityEntity?>?
}