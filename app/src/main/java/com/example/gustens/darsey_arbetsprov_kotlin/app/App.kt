package com.example.gustens.darsey_arbetsprov_kotlin.app

import android.app.Application

import com.example.gustens.darsey_arbetsprov_kotlin.di.MainComponent
import com.example.gustens.darsey_arbetsprov_kotlin.di.ApiModule
import com.example.gustens.darsey_arbetsprov_kotlin.di.AppModule
import com.example.gustens.darsey_arbetsprov_kotlin.di.DaggerMainComponent

class App: Application() {

    lateinit var mainComponent: MainComponent


    override fun onCreate() {
        super.onCreate()

        initDependencies()
    }

    private fun initDependencies() {
        mainComponent = DaggerMainComponent.builder()
                .appModule(AppModule(applicationContext))
                .apiModule(ApiModule(Constants.BASE_URL, Constants.API_KEY))
                .build()

    }


}