package com.vp.weatherapp.di

import android.app.Application
import android.arch.persistence.room.Room
import com.vp.weatherapp.data.local.AppDatabase
import com.vp.weatherapp.data.local.dao.WeatherDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext


val databaseModule = applicationContext {

    bean { createDatabase(androidApplication()) }

    bean { createWeatherDao(get()) }

//    bean { Room.databaseBuilder(get(), AppDatabase::class.java, "weather.db").build().weatherDao() }
}

fun createDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "weather.db").build()


fun createWeatherDao(db: AppDatabase): WeatherDao = db.weatherDao()