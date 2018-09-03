package com.suroid.weatherapp.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.suroid.weatherapp.models.City

/**
 * The Room database that contains the Data table
 */
@Database(entities = [City::class], version = 1,exportSchema = false)
abstract class WeatherDb : RoomDatabase() {

    abstract fun cityDao(): CityDao

}