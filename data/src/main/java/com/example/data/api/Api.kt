package com.example.data.api


import com.example.domain.model.DailyForecastResponse
import com.example.domain.model.ForecastResponse
import com.example.domain.model.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {

    /*
    For info: The Api key query is added in the ApiModule
     */

    @GET("weather")
    fun getWeatherNew(@Query("q") city: String): Observable<WeatherResponse>

    @GET("weather")
    fun getWeatherByCoord(@Query("lat") latitude: String, @Query("lon") longitude: String): Observable<WeatherResponse>

    // forecast hourly
    @GET("forecast")
    fun getForecastByCoord(@Query("lat") latitude: String, @Query("lon") longitude: String): Observable<ForecastResponse>

    // forecast daily
    @GET("forecast/daily")
    fun getDailyForecastByCoord(@Query("lat") latitude: String, @Query("lon") longitude: String, @Query("cnt") cnt : Int): Observable<DailyForecastResponse>


}