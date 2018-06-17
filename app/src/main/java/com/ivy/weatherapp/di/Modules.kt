package com.ivy.weatherapp.di

import android.arch.persistence.room.Room
import com.ivy.weatherapp.BuildConfig
import com.ivy.weatherapp.data.local.WeatherDatabase
import com.ivy.weatherapp.data.remote.WeatherApi
import com.ivy.weatherapp.data.repository.WeatherRepository
import com.ivy.weatherapp.data.repository.WeatherRepositoryImpl
import com.ivy.weatherapp.system.LocationManager
import com.ivy.weatherapp.system.LocationManagerImpl
import com.ivy.weatherapp.system.PermissionManager
import com.ivy.weatherapp.system.PermissionManagerImpl
import com.ivy.weatherapp.ui.WeatherViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun getWeatherAppModules() = listOf(weatherModule)

val weatherModule = applicationContext {

    bean { Room.databaseBuilder(androidApplication(), WeatherDatabase::class.java, "weather_db").build() }
    bean { get<WeatherDatabase>().weatherDao() }
    bean { WeatherRepositoryImpl(get(), get()) as WeatherRepository }
    bean { PermissionManagerImpl(get()) as PermissionManager }
    bean {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = when (BuildConfig.DEBUG) {
            true -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
        OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(15L, TimeUnit.SECONDS)
                .readTimeout(15L, TimeUnit.SECONDS)
                .build()
    }
    bean {
        Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
    }
    bean { LocationManagerImpl(get()) as LocationManager }

    viewModel { WeatherViewModel(get(), get(), get()) }
}