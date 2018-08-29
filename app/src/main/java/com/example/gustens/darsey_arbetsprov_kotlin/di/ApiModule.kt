package com.example.gustens.darsey_arbetsprov_kotlin.di

import com.example.data.api.Api
import com.example.gustens.darsey_arbetsprov_kotlin.app.Constants
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Yossi Segev on 11/11/2017.
 */
@Module
class ApiModule(private val apiBaseUrl: String, private val apiKey: String) {

    @Provides
    @Singleton
    internal fun provideGsonConverter(): Converter.Factory {
        return GsonConverterFactory.create()

    }


    @Provides
    @Singleton
    internal fun provideRetrofit(gsonConverterFactory: Converter.Factory): Retrofit {

        val interceptors = arrayListOf<Interceptor>()

        val keyInterceptor = Interceptor { chain ->

            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("key", Constants.API_KEY)
                    .build()

            val requestBuilder = original.newBuilder()
                    .url(url)

            val request = requestBuilder.build()
            return@Interceptor chain.proceed(request)
        }

        interceptors.add(keyInterceptor)

        val logging : HttpLoggingInterceptor  = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        interceptors.add(logging)

        val clientBuilder = OkHttpClient.Builder()

        if (!interceptors.isEmpty()) {
            interceptors.forEach { interceptor ->
                clientBuilder.addInterceptor(interceptor)
            }
        }

        return Retrofit.Builder()
                .baseUrl(apiBaseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder.build())
                .build()
    }


    @Provides
    @Singleton
    internal fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create<Api>(Api::class.java!!)
    }

}