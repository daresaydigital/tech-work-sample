package com.ukhanoff.rainbeforeseven.di.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ukhanoff.rainbeforeseven.api.WeatherService
import com.ukhanoff.rainbeforeseven.di.util.ForApplication
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//TODO Move to gradle
const val WEATHER_API_KEY = ""
const val WEATHER_API_ENDPOINT = "http://worksample-api.herokuapp.com/"

@Module(includes = [DataModuleBindings::class])
object DataModule {

    @Provides
    @JvmStatic
    @ForApplication
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @JvmStatic
    @ForApplication
    fun provideApiRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
            .baseUrl(WEATHER_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @JvmStatic
    @ForApplication
    fun provideApiEndpoint(retrofit: Retrofit): WeatherService = retrofit.create(WeatherService::class.java)

}

@Module
interface DataModuleBindings
