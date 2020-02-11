package com.sneha.weatherapp.data.repository

import com.sneha.weatherapp.data.local.db.DatabaseService
import com.sneha.weatherapp.data.model.Weather
import com.sneha.weatherapp.data.remote.NetworkService
import com.sneha.weatherapp.data.remote.response.DailyForecastResponse
import com.sneha.weatherapp.data.remote.response.TodayForecastResponse
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun fetchWeatherData(cityName: String): Single<Weather> =
        networkService.getWeatherData(cityName = cityName)

    fun fetchTodayForecast(cityId: Long): Single<TodayForecastResponse> =
        networkService.getTodayForecast(cityId, 7)

    fun fetchDailyForecast(cityId: Long): Single<DailyForecastResponse> =
        networkService.getDailyForecast(cityId, 7)
}