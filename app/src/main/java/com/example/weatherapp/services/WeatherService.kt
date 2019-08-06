package com.example.weatherapp.services

import com.example.weatherapp.models.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    fun getCurrentByLocation(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("APPID") apiKey: String): Call<WeatherData>

    @GET("weather")
    fun getCurrentByCity(@Query("id") cityId: Long, @Query("APPID") apiKey: String): Call<WeatherData>
}