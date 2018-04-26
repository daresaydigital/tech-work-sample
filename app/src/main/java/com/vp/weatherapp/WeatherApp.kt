package com.vp.weatherapp

import android.app.Application
import com.vp.weatherapp.di.databaseModule
import com.vp.weatherapp.di.networkModule
import com.vp.weatherapp.di.weatherApp
import com.vp.weatherapp.di.weatherModule
import org.koin.ContextCallback
import org.koin.android.ext.android.startKoin
import org.koin.standalone.StandAloneContext.registerContextCallBack


class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, weatherApp + databaseModule + networkModule)

        registerContextCallBack(object : ContextCallback {
            override fun onContextReleased(contextName: String) {
                println("Context $contextName has been dropped")
            }
        })
    }
}