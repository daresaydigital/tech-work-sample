package com.russellmorris.showweather.service.api

import com.russellmorris.showweather.service.dao.WeatherDAO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather?")
    fun getWeather(
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?,
        @Query("key") key: String,
        @Query("units") units: String
    ) : Single<WeatherDAO>
}