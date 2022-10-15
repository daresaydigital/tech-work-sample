package com.github.hramogin.movieapp

import android.app.Application
import com.github.hramogin.movieapp.di.modules.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    applicationModule,
                )
            )
        }
    }
}