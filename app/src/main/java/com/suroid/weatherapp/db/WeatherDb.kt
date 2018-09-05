package com.suroid.weatherapp.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.suroid.weatherapp.models.City
import com.suroid.weatherapp.models.CityWeatherEntity

/**
 * The Room database that contains the Data table
 */
@Database(entities = [City::class, CityWeatherEntity::class], version = 8, exportSchema = false)
@TypeConverters(WeatherModelConverter::class, TemperatureModelConverter::class)
abstract class WeatherDb : RoomDatabase() {

    abstract fun cityDao(): CityDao

    abstract fun cityWeatherDao(): CityWeatherDao

}