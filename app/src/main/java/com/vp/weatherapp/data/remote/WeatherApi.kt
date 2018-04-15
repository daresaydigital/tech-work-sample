package com.vp.weatherapp.data.remote

import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("weather")
    fun getWeatherByCity(@Query("q") cityName: String): Flowable<WeatherResponse>

    @GET("weather")
    fun getWeatherByLatLon(@Query("lat") lat: Float,
                           @Query("lon") lon: Float): Flowable<WeatherResponse>

    // Hourly
    @GET("forecast")
    fun getHourlyForecastByCity(@Query("q") cityName: String): Flowable<HourlyForecastResponse>

    @GET("forecast")
    fun getHourlyForecastByLatLon(
            @Query("lat") lat: Float,
            @Query("lon") lon: Float): Flowable<HourlyForecastResponse>

    // Daily
    @GET("forecast/daily")
    fun getDailyForecastByCity(
            @Query("q") cityName: String): Flowable<DailyForecastResponse>

    @GET("forecast/daily")
    fun getDailyForecastByLatLon(
            @Query("lat") lat: Float,
            @Query("lon") lon: Float): Flowable<DailyForecastResponse>
}
