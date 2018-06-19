package com.ukhanoff.rainbeforeseven.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ukhanoff.rainbeforeseven.data.WeatherGlobalModel
import com.ukhanoff.rainbeforeseven.data.ForecastWeatherModel
import com.ukhanoff.rainbeforeseven.repository.WeatherRepository
import javax.inject.Inject


class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    fun getCurrentWeather(lat: Double, lon: Double): MutableLiveData<WeatherGlobalModel> {
        return repository.getCurrentWeather(lat,lon)
    }

    fun getForecastWeather(lat: Double, lon: Double): MutableLiveData<ForecastWeatherModel> {
        return repository.getForecastWeather(lat, lon)
    }

}
