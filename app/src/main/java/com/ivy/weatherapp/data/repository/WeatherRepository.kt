package com.ivy.weatherapp.data.repository

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.ivy.weatherapp.data.model.Weather

@Dao
interface WeatherRepository {

    @Insert
    fun insert(weather: Weather)

    @Query("SELECT * FROM weather LIMIT 1")
    fun get(): LiveData<Weather>
}