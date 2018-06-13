package com.deresay.sayweather

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun fetchWeather(@Query("lat") latitude: Double,
                     @Query("lon") longitude: Double,
                     @Query("key") apiKey: String): rx.Observable<WeatherModel>
}