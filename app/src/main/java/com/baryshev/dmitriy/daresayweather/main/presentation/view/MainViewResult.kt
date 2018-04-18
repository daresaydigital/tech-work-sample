package com.baryshev.dmitriy.daresayweather.main.presentation.view

import com.baryshev.dmitriy.daresayweather.main.domain.MainData

sealed class CurrentWeatherViewResult {
    data class Success(val data: MainData.CurrentWeather) : CurrentWeatherViewResult()
    data class Error(val message: String, val error: Throwable? = null) : CurrentWeatherViewResult()
    object Progress : CurrentWeatherViewResult()
}

sealed class ForecastViewResult {
    data class Success(val data: List<MainData.Forecast>) : ForecastViewResult()
    data class Error(val message: String, val error: Throwable? = null) : ForecastViewResult()
    object Progress : ForecastViewResult()
}
