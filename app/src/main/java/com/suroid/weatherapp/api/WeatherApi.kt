package com.suroid.weatherapp.api

import com.suroid.weatherapp.models.remote.WeatherResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API access point
 */
interface WeatherApi {

    @GET("weather")
    fun getWeatherWithId(@Query("id") id: Int): Single<WeatherResponseModel>

    @GET("weather")
    fun getWeatherWithLatLong(@Query("lat") lat: Double, @Query("lon") long: Double): Single<WeatherResponseModel>
}
