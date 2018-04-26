package com.vp.weatherapp.di

import android.app.Application
import android.arch.persistence.room.Room
import com.vp.weatherapp.data.local.AppDatabase
import com.vp.weatherapp.data.local.DatabaseHelper
import com.vp.weatherapp.data.local.dao.WeatherDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext


val databaseModule = applicationContext {

    bean { createDatabaseHelper(get()) }

    bean { createDatabase(androidApplication(), get()) }

    bean { createWeatherDao(get()) }

//    bean { Room.databaseBuilder(get(), AppDatabase::class.java, "weather.db").build().weatherDao() }
}

fun createDatabase(app: Application, dbHelper: DatabaseHelper): AppDatabase {

    return Room.databaseBuilder(app, AppDatabase::class.java, "weather.db")
            .addCallback(dbHelper)
            .build()
}


fun createDatabaseHelper(app: Application): DatabaseHelper = DatabaseHelper(app)

fun createWeatherDao(db: AppDatabase): WeatherDao = db.weatherDao()