package com.ivy.weatherapp.data.repository

import android.arch.lifecycle.LiveData
import com.ivy.weatherapp.data.local.WeatherDao
import com.ivy.weatherapp.data.local.model.Weather
import com.ivy.weatherapp.data.remote.WeatherApi
import com.ivy.weatherapp.extention.convert
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch

interface WeatherRepository {
    fun get(): LiveData<Weather>
    fun insert(weather: Weather): Job
}

class WeatherRepositoryImpl(
        private val weatherDao: WeatherDao,
        private val weatherApi: WeatherApi
) : WeatherRepository {

    override fun get(): LiveData<Weather> {
        launch {
            val response = weatherApi.getWeather(59.334591, 18.063240, "").execute()
            if (response == null) {
                //TODO handle network error
            } else if (!response.isSuccessful) {
                //TODO handle response error
            } else {
                val weatherResponse = response.body()
                if (weatherResponse == null) {
                    //TODO handle conversion error
                } else {
                    val weather = weatherResponse.convert()
                    insert(weather)
                }
            }
        }
        return weatherDao.get()
    }

    override fun insert(weather: Weather) = launch { weatherDao.insert(weather) }
}