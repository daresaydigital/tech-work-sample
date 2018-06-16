package com.ivy.weatherapp.data.remote

import com.ivy.weatherapp.data.remote.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    fun getWeather(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("key") key: String): Call<WeatherResponse>
}
