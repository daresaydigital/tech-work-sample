package com.ivy.weatherapp.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.ivy.weatherapp.data.local.model.Weather
import com.ivy.weatherapp.data.repository.WeatherRepository
import com.ivy.weatherapp.system.Permissions
import com.ivy.weatherapp.ui.base.BaseViewModel
import java.util.*

class WeatherViewModel(
        private val weatherRepository: WeatherRepository,
        private val permissions: Permissions
) : BaseViewModel() {

    val weather: LiveData<Weather> = weatherRepository.get()
    val permission: MutableLiveData<Boolean> = MutableLiveData()

    init {
        permission.postValue(permissions.hasLocationPermission())
    }

    fun mockData() {
        val random = Random()
        val mockWeather = Weather(
                temp = random.nextDouble(),
                name = "Stockholm",
                tempMax = random.nextInt(50),
                tempMin = random.nextInt(12),
                description = "Always Sunny"
        )
        weatherRepository.insert(mockWeather)
    }
}