package com.deresay.sayweather.utils

import com.deresay.sayweather.models.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Remote HTTP API calls.
 */
interface ApiInterface {
    @GET("weather")
    fun fetchWeather(@Query("lat") latitude: Double,
                     @Query("lon") longitude: Double,
                     @Query("key") apiKey: String): rx.Observable<WeatherModel>
}