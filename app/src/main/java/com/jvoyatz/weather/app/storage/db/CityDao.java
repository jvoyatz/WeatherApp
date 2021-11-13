package com.jvoyatz.weather.app.storage.db;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jvoyatz.weather.app.models.entities.CityEntity;

import java.util.List;

/**
 * Inteface for accessing database of City data
 */
@Dao
public interface CityDao {
    @Query("SELECT *  FROM CityEntity")
    List<CityEntity> getAllList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CityEntity> cities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CityEntity city);

    @Query("SELECT * FROM CityEntity WHERE name LIKE '%' || :query || '%' OR  region LIKE '%' ||  :query || '%' OR country LIKE '%' || :query || '%'")
    Cursor findByNameCursor(String query);

    @Query("SELECT * FROM CityEntity WHERE name LIKE '%' || :city || '%' AND  region LIKE '%' ||  :region || '%' AND country LIKE '%' || :country || '%' limit 1")
    LiveData<CityEntity> findByMultipleCriteria(@NonNull String city, @NonNull String region, @NonNull String country);

    @Query("SELECT * FROM CityEntity WHERE name LIKE '%' || :city || '%' AND  region LIKE '%' ||  :region || '%' AND country LIKE '%' || :country || '%' limit 1")
    LiveData<CityEntity> findByMultipleCriteriaLimit1(@NonNull String city, @NonNull String region, @NonNull String country);

    @Query("SELECT * FROM CityEntity WHERE isFavorite = 1")
    List<CityEntity> getFavoriteCities();


//    @Query("SELECT * FROM FavoriteCityEntity")
//    LiveData<List<FavoriteCityEntity>> getAllFavoriteCities();
//
//    @Insert//(onConflict = OnConflictStrategy.REPLACE)
//    void insertFavoriteCity(@NonNull FavoriteCityEntity favoriteCityEntity);
//
//    @Query("SELECT * FROM FavoriteCityEntity WHERE name LIKE '%' || :city || '%' AND  region LIKE '%' ||  :region || '%' AND country LIKE '%' || :country || '%' limit 1")
//    LiveData<FavoriteCityEntity> findFavoriteByMultipleCriteria(@NonNull String city, @NonNull String region, @NonNull String country);


//
//    @Delete
//    void delete(User user);
}
