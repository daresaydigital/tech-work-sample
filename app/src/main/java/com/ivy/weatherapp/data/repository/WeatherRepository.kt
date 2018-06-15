package com.ivy.weatherapp.data.repository

import android.arch.lifecycle.LiveData
import com.ivy.weatherapp.data.local.WeatherDao
import com.ivy.weatherapp.data.local.model.Weather
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch

interface WeatherRepository {
    fun get(): LiveData<Weather>
    fun insert(weather: Weather): Job
}

class WeatherRepositoryImpl(
        private val weatherDao: WeatherDao
) : WeatherRepository {

    override fun get(): LiveData<Weather> {
        return weatherDao.get()
    }

    override fun insert(weather: Weather) = launch { weatherDao.insert(weather) }
}