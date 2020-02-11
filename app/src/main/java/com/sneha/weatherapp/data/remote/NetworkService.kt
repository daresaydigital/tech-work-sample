package com.sneha.weatherapp.data.remote

import com.sneha.weatherapp.data.model.Weather
import com.sneha.weatherapp.data.remote.response.DailyForecastResponse
import com.sneha.weatherapp.data.remote.response.TodayForecastResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET(Endpoints.WEATHER)
    fun getWeatherData(
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?,
        @Query(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<Weather>

    @GET(Endpoints.FORECAST)
    fun getTodayForecast(
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?,
        @Query("cnt") count: Int,
        @Query(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<TodayForecastResponse>

    @GET(Endpoints.FORECAST_DAILY)
    fun getDailyForecast(
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?,
        @Query("cnt") count: Int,
        @Query(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<DailyForecastResponse>
}