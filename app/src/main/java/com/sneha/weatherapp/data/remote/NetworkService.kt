package com.sneha.weatherapp.data.remote

import com.sneha.weatherapp.data.model.DailyForecast
import com.sneha.weatherapp.data.model.Forecast
import com.sneha.weatherapp.data.model.Weather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET(Endpoints.WEATHER)
    fun getWeatherData(
        @Query("q") cityName: String,
        @Query(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<Weather>

    @GET(Endpoints.FORECAST_DAILY)
    fun getDailyForecast(
        @Query("id") cityId: Long,
        @Query("cnt") count: Int,
        @Query(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<DailyForecast>

    @GET(Endpoints.FORECAST)
    fun getTodayForecast(
        @Query("id") cityId: Long,
        @Query("cnt") count: Int,
        @Query(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<Forecast>
}