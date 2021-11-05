package com.jvoyatz.weather.app.storage.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.jvoyatz.weather.app.models.api.entities.CityEntity;

import java.util.List;

@Dao
public interface CityDao {
    @Query("SELECT *, rowid  FROM CityEntity")
    LiveData<List<CityEntity>> getAll();

    //debug

    @Query("SELECT *, rowid  FROM CityEntity")
    List<CityEntity> getAllList();

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    List<User> loadAllByIds(int[] userIds);
//@Query("SELECT * FROM streets WHERE greek_low LIKE '%' || :streetName || '%'")
    @Query("SELECT *, rowid FROM CityEntity WHERE name LIKE '%' || :query || '%' OR  region LIKE '%' ||  :query || '%' OR country LIKE '%' || :query || '%'")
    LiveData<List<CityEntity>> findByName(String query);

    @Insert
    void insertAll(List<CityEntity> cities);

//
//    @Delete
//    void delete(User user);
}
