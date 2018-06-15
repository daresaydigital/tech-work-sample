package com.ivy.weatherapp.ui

import android.arch.lifecycle.LiveData
import com.ivy.weatherapp.data.local.model.Weather
import com.ivy.weatherapp.data.repository.WeatherRepository
import com.ivy.weatherapp.ui.base.BaseViewModel
import java.util.*

class WeatherViewModel(
        private val weatherRepository: WeatherRepository
) : BaseViewModel() {

    val weather: LiveData<Weather> = weatherRepository.get()

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