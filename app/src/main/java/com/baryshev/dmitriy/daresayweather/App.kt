package com.baryshev.dmitriy.daresayweather

import android.app.Application
import android.content.Context
import com.baryshev.dmitriy.daresayweather.common.di.AppComponent
import com.baryshev.dmitriy.daresayweather.common.di.AppModule
import com.baryshev.dmitriy.daresayweather.common.di.DaggerAppComponent

/**
 * 4/10/2018.
 */
class App : Application() {

    var appComponent : AppComponent? = null
        private set

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun context() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}

val Context.app: App get() = applicationContext as App