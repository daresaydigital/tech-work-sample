package com.suroid.weatherapp.api

import com.suroid.weatherapp.models.remote.WeatherResponseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API access point
 */
interface WeatherApi {

    @GET("weather")
    fun getWeather(@Query("q") query: String): Observable<WeatherResponseModel>
}
