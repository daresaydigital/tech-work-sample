package com.suroid.weatherapp.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.suroid.weatherapp.models.City
import io.reactivex.Single

@Dao
abstract class CityDao : BaseDao<City> {

    /**
     * Get all cities from the Data table.
     */
    @Query("SELECT * FROM City")
    abstract fun getAllCities(): Single<List<City>>
}