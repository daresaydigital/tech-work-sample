package com.ivy.weatherapp.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.ivy.weatherapp.data.model.Weather
import com.ivy.weatherapp.data.repository.WeatherRepository

@Database(entities = [Weather::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherRepository(): WeatherRepository
}