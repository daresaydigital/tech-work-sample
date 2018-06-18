package com.ukhanoff.rainbeforeseven.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ukhanoff.rainbeforeseven.model.CurrentWeatherModel
import com.ukhanoff.rainbeforeseven.repository.WeatherRepository
import javax.inject.Inject


class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    fun getCurrentWeather(lat: Double, lon: Double): MutableLiveData<CurrentWeatherModel> {
        return repository.getCurrentWeather(lat,lon)
    }

}
