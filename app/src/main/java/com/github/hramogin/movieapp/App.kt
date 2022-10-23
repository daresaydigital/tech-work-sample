package com.github.hramogin.movieapp

import android.app.Application
import com.github.hramogin.movieapp.di.modules.applicationModule
import com.github.hramogin.movieapp.di.modules.databaseModule
import com.github.hramogin.movieapp.di.modules.mapperModule
import com.github.hramogin.movieapp.di.modules.networkModule
import com.github.hramogin.movieapp.di.modules.networkServiceModule
import com.github.hramogin.movieapp.di.modules.repositoryModule
import com.github.hramogin.movieapp.di.modules.useCaseModule
import com.github.hramogin.movieapp.di.modules.viewModelModule
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
                    viewModelModule,
                    repositoryModule,
                    useCaseModule,
                    networkServiceModule,
                    networkModule,
                    databaseModule,
                    mapperModule,
                )
            )
        }
    }
}