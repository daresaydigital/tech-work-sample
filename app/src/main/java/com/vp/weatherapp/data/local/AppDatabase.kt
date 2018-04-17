package com.vp.weatherapp.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.vp.weatherapp.data.local.dao.WeatherDao
import com.vp.weatherapp.data.local.entity.CityEntity
import com.vp.weatherapp.data.local.entity.DailyForecastEntity
import com.vp.weatherapp.data.local.entity.HourlyForecastEntity
import com.vp.weatherapp.data.local.entity.SelectedCityEntity


@Database(
        entities = [HourlyForecastEntity::class, DailyForecastEntity::class, CityEntity::class,
        SelectedCityEntity::class],
        version = 1,
        exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}