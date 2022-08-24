package com.example.movieapp.network

import androidx.viewbinding.BuildConfig
import com.example.movieapp.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ServiceBuilder @Inject constructor(private val httpClient: OkHttpClient.Builder) {

    var moviesService: MoviesApi

    init {

        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.HEADERS
        }
        httpClient.addInterceptor(logging)
        moviesService = buildService().create(MoviesApi::class.java)
    }

    private fun buildService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }
}