package com.example.weatherapp.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.weatherapp.database.entities.City
import com.example.weatherapp.database.entities.CityWeather

@Dao
abstract class CityWeatherDao : BaseDao<CityWeather> {
    @Query("SELECT * FROM cityweather")
    abstract fun getAll(): List<CityWeather>
}