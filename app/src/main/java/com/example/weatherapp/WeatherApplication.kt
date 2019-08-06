package com.example.weatherapp

import android.app.Application
import android.content.Context
import com.example.weatherapp.database.daos.CityDao
import com.example.weatherapp.koin.databaseModule
import com.example.weatherapp.koin.networkModule
import com.example.weatherapp.koin.viewModelModule
import com.example.weatherapp.utils.runOnIoThread
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.context.startKoin

class WeatherApplication : Application() {
    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this

        startKoin {
            androidContext(this@WeatherApplication)
            modules(listOf(networkModule, databaseModule, viewModelModule))
        }

        runOnIoThread {
            val cityDao: CityDao = get()
            cityDao.getAll()
        }
    }


}