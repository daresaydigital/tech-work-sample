package com.vp.weatherapp.di

import com.vp.weatherapp.common.ApiKeyInterceptor
import com.vp.weatherapp.data.remote.EnvelopeConverterFactory
import com.vp.weatherapp.data.remote.WeatherApi
import com.vp.weatherapp.di.NetworkProperties.SERVER_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = applicationContext {

    bean { createOkHttpClient() }

    bean { createWebService<WeatherApi>(get(), getProperty(SERVER_URL)) }
}

object NetworkProperties {
    const val SERVER_URL = "SERVER_URL"
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
            .connectTimeout(15L, TimeUnit.SECONDS)
            .readTimeout(15L, TimeUnit.SECONDS)
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .build()
}


inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
//            .addConverterFactory(EnvelopeConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    return retrofit.create(T::class.java)
}