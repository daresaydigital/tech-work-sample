package com.example.malek.weatherapp.utils

import com.example.malek.weatherapp.models.CurrentWeather
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/weather")
    fun getCurrentWeather(@Query("lat") lat: String, @Query("lon") lon: String, @Query("key") apiKey: String): Single<CurrentWeather>

    companion object {
        fun create(): ApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            val retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl("http://worksample-api.herokuapp.com")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create(ApiService::class.java)
        }

    }
}

object ApiManager {
    val apiService = ApiService.create()
    val apiKey = "62fc4256-8f8c-11e5-8994-feff819cdc9f"
}