package com.example.domain

import com.example.domain.model.DailyForecastResponse
import com.example.domain.model.ForecastResponse
import com.example.domain.model.WeatherResponse
import io.reactivex.Observable


interface WeatherDataStore {

    fun getWeather(city :String): Observable<WeatherResponse>

    fun getWeatherByCoord(lat: String, lon: String): Observable<WeatherResponse>

    fun getForecastByCoord(lat: String, lon: String): Observable<ForecastResponse>

    fun getDailyForecastByCoord(lat: String, lon: String, days : Int): Observable<DailyForecastResponse>

}