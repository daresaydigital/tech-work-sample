package com.ivy.weatherapp.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.ivy.weatherapp.BuildConfig
import com.ivy.weatherapp.data.local.WeatherDao
import com.ivy.weatherapp.data.local.model.Weather
import com.ivy.weatherapp.data.remote.WeatherApi
import com.ivy.weatherapp.extention.convert
import com.ivy.weatherapp.util.Failure
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch

interface WeatherRepository {
    fun getWeather(): LiveData<Weather>
    fun getError(): MutableLiveData<Failure>
    fun fetch(): Job
}

class WeatherRepositoryImpl(
        private val weatherDao: WeatherDao,
        private val weatherApi: WeatherApi
) : WeatherRepository {

    private val data: LiveData<Weather> = weatherDao.get()
    private val error = MutableLiveData<Failure>()

    override fun getWeather() = data

    override fun getError() = error

    override fun fetch() = launch {
        val response = try {
            weatherApi.getWeather(59.334591, 18.063240, BuildConfig.WEATHER_API_KEY).execute()
        } catch (e: Exception) {
            null
        }
        if (response == null) {
            error.postValue(Failure.NetworkConnection())
        } else if (!response.isSuccessful) {
            error.postValue(Failure.ServerError())
        } else {
            val weatherResponse = response.body()
            if (weatherResponse == null) {
                error.postValue(Failure.DataError())
            } else {
                val weather = weatherResponse.convert()
                weatherDao.insert(weather)
            }
        }
    }
}