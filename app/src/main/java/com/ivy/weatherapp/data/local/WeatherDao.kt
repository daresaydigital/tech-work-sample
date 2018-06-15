package com.ivy.weatherapp.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.ivy.weatherapp.data.local.model.Weather

@Dao
interface WeatherDao {

    @Insert
    fun insert(weather: Weather)

    @Query("SELECT * FROM weather LIMIT 1")
    fun get(): LiveData<Weather>
}