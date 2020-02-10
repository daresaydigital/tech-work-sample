package com.sneha.weatherapp.data.repository

import com.sneha.weatherapp.data.local.db.DatabaseService
import com.sneha.weatherapp.data.model.DailyForecast
import com.sneha.weatherapp.data.model.Forecast
import com.sneha.weatherapp.data.model.Weather
import com.sneha.weatherapp.data.remote.NetworkService
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun fetchWeatherData(cityName: String): Single<Weather> =
        networkService.getWeatherData(cityName = cityName)

    fun fetchTodayForecast(cityId: Long): Single<Forecast> =
        networkService.getTodayForecast(cityId, 7)

    fun fetchDailyForecast(cityId: Long): Single<DailyForecast> =
        networkService.getDailyForecast(cityId, 7)
}