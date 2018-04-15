package com.vp.weatherapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.vp.weatherapp.R
import com.vp.weatherapp.data.WeatherRepository
import com.vp.weatherapp.data.local.entity.HourlyForecastEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val repo: WeatherRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        repo.getHourlyForecast("Stockholm", "SE")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { forecasts -> printForecasts(forecasts) },
                        { throwable -> handleError(throwable) } )
    }

    private fun printForecasts(forecasts: List<HourlyForecastEntity>) {
        println(" ============= MAIN ACTIVITY ON NEXT")
        forecasts.forEach {
            println(it)
        }
    }

    private fun handleError(throwable: Throwable) {
        throwable.printStackTrace()
    }
}
