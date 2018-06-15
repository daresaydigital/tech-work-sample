package com.ivy.weatherapp.data.repository

import android.arch.lifecycle.LiveData
import com.ivy.weatherapp.data.local.WeatherDao
import com.ivy.weatherapp.data.local.model.Weather

interface WeatherRepository {
    fun getWearther(): LiveData<Weather>
}

class WeatherRepositoryImpl(
        private val weatherDao: WeatherDao
) : WeatherRepository {

    override fun getWearther(): LiveData<Weather> {
        return weatherDao.get()
    }
}