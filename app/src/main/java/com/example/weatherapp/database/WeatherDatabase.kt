package com.example.weatherapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.weatherapp.database.daos.CityDao
import com.example.weatherapp.database.daos.CityWeatherDao
import com.example.weatherapp.database.entities.City
import com.example.weatherapp.database.entities.CityWeather


@Database(entities = [City::class, CityWeather::class], version = 1)
@TypeConverters(WeatherConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun cityWeatherDao(): CityWeatherDao
}