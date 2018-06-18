package com.ukhanoff.rainbeforeseven.api

import com.ukhanoff.rainbeforeseven.model.CurrentWeatherModel
import com.ukhanoff.rainbeforeseven.model.ForecastWeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {

    @GET("weather?key=111")
    fun getCurrentWeather(@Query("lat") latitude: Double?,
                          @Query("lon") longitude: Double?): Call<CurrentWeatherModel>

    @GET("forecast?key=111")
    fun getForecastWeather(@Query("lat") latitude: Double?,
                           @Query("lon") longitude: Double?): Call<ForecastWeatherModel>

}
