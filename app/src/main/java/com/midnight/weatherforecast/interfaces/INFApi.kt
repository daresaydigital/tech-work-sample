package com.midnight.weatherforecast.interfaces

import com.midnight.weatherforecast.models.modelsResponse.ModelCurrentWeater
import com.midnight.weatherforecast.models.modelsResponse.ModelForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface INFApi {
    @GET("weather")
    abstract fun currentWeaterByCityName(@Query("q") cityName: String,@Query("key") token: String): Call<ModelCurrentWeater>

    @GET("weather")
    abstract fun currentWeaterByCityId(@Query("id") cityId: String,@Query("key") token: String): Call<ModelCurrentWeater>

    @GET("weather")
    abstract fun currentWeaterByCityGeo(@Query("lat") cityLat: String,@Query("lon") cityLon: String,@Query("key") token: String): Call<ModelCurrentWeater>

    @GET("weather")
    abstract fun currentWeaterByCityZip(@Query("zip") cityZip: String,@Query("key") token: String): Call<ModelCurrentWeater>

    @GET("weather")
    abstract fun currentWeaterByCitiesId(@Query("id") cityId: String,@Query("key") token: String): Call<ModelCurrentWeater>

    @GET("forecast")
    abstract fun forecastWeaterByCityName(@Query("q") cityName: String,@Query("key") token: String): Call<ModelForecast>

}