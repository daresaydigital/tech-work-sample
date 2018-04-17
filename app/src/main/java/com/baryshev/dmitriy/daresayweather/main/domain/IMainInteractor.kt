package com.baryshev.dmitriy.daresayweather.main.domain

import io.reactivex.Single

interface IMainInteractor {

    fun getCurrentCityWeather(fromCache: Boolean = true): Single<MainData.CurrentWeather>

    fun getWeatherByCityName(city: String): Single<MainData.CurrentWeather>

    fun getCurrentCityForecast(fromCache: Boolean = true): Single<List<MainData.Forecast>>

    fun getForecastByCityName(city: String): Single<List<MainData.Forecast>>

    fun clear()
}