package com.jvoyatz.weather.app.storage.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jvoyatz.weather.app.models.entities.CityEntity;

@Database(entities = {CityEntity.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract CityDao cityDAO();

   public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Empty implementation, because the schema isn't changing.
        }
    };
}