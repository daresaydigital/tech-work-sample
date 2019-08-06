package com.example.weatherapp.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.weatherapp.models.City

@Dao
abstract class CityDao : BaseDao<City> {
    @Query("SELECT * FROM city")
    abstract fun getAll(): List<City>

    @Query("SELECT * FROM city WHERE name LIKE :pattern LIMIT 10")
    abstract fun getByName(pattern: String): List<City>
}