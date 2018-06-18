package com.ukhanoff.rainbeforeseven.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ukhanoff.rainbeforeseven.model.CurrentWeatherModel
import com.ukhanoff.rainbeforeseven.repository.WeatherRepository
import javax.inject.Inject


class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {


    private var currentWeather = MutableLiveData<CurrentWeatherModel>()

    fun init() {
        currentWeather = this.repository.getCurrentWeather()
    }

    fun getCurrentWeather(): MutableLiveData<CurrentWeatherModel> {
        return currentWeather
    }

}
