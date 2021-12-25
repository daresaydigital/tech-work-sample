package com.arashafsharpour.daresaymovie.infrastructure.di

import com.arashafsharpour.daresaymovie.infrastructure.network.ApiCallHeaderInterceptor
import com.arashafsharpour.daresaymovie.persistence.TIMEOUT
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https:/api.themoviedb.org/")
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder().apply {
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        }
            .addInterceptor(ApiCallHeaderInterceptor())
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideJsonConvertorFactory(objectMapper: ObjectMapper): Converter.Factory {
        return JacksonConverterFactory.create(objectMapper)
    }

    @Singleton
    @Provides
    fun provideJackSonObjectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper().registerKotlinModule()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        objectMapper.dateFormat = SimpleDateFormat(DATE_TIME_PATTERN, Locale.ENGLISH)
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)

        return objectMapper
    }
}