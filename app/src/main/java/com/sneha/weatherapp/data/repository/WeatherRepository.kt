package com.sneha.weatherapp.data.repository

import com.sneha.weatherapp.data.local.db.DatabaseService
import com.sneha.weatherapp.data.local.prefs.UserPreferences
import com.sneha.weatherapp.data.model.LocationData
import com.sneha.weatherapp.data.model.Weather
import com.sneha.weatherapp.data.remote.NetworkService
import com.sneha.weatherapp.data.remote.response.DailyForecastResponse
import com.sneha.weatherapp.data.remote.response.TodayForecastResponse
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val userPreferences: UserPreferences
) {

    fun getLocationData () : LocationData = LocationData(userPreferences.getLongitude()?.toDouble(),
        userPreferences.getLatitude()?.toDouble())

    fun fetchWeatherData(): Single<Weather> =
        networkService.getWeatherData(userPreferences.getLatitude(), userPreferences.getLongitude())

    fun fetchTodayForecast(): Single<TodayForecastResponse> =
        networkService.getTodayForecast(userPreferences.getLatitude(),userPreferences.getLongitude(), 7)

    fun fetchDailyForecast(): Single<DailyForecastResponse> =
        networkService.getDailyForecast(userPreferences.getLatitude(),userPreferences.getLongitude(), 7)
}