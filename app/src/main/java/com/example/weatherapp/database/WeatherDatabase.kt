package com.example.weatherapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.database.daos.CityDao
import com.example.weatherapp.models.City


@Database(entities = [City::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}