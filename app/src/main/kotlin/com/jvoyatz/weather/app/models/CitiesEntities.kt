package com.jvoyatz.weather.app.models.entities.kotlin

import androidx.room.Embedded
import androidx.room.Entity

@Entity(primaryKeys = ["_id", "name", "region", "country"])
data class CityEntity(
    val name: String,
    val country: String,
    val region: String,
    val latitude: String? = null,
    val longitude: String? = null,
    val population: String? = null,
    val weatherUrl: String? = null,
    val isFavorite: Boolean = false,
    @Embedded val timezone: TimezoneEntity?
    )

@Entity
data class TimezoneEntity (val offset: String, val zone: String)

fun main() {
    val city = CityEntity("livadeia", "greece", "viotia", timezone = null);

    println("city is $city")
}