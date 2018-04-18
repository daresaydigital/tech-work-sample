package com.baryshev.dmitriy.daresayweather.common.data.network

import com.baryshev.dmitriy.daresayweather.common.data.weather.pojo.ForecastResponse
import com.baryshev.dmitriy.daresayweather.common.data.weather.pojo.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather")
    fun getWeatherByCoords(@Query("lat") lat: Float, @Query("lon") lon: Float): Single<WeatherResponse>

    @GET("weather")
    fun getWeatherByCityName(@Query("q") city: String): Single<WeatherResponse>

    @GET("forecast/daily")
    fun getForecastWeatherByCoord(@Query("lat") lat: Float, @Query("lon") lon: Float): Single<ForecastResponse>

    @GET("forecast/daily")
    fun getForecastWeatherByCityName(@Query("q") city: String): Single<ForecastResponse>

}