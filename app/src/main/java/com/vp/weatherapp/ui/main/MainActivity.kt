package com.vp.weatherapp.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import com.vp.weatherapp.di.Params.MAIN_VIEW
import com.vp.weatherapp.ui.initial.InitialActivity
import com.vp.weatherapp.ui.main.paging.BackgroundManagerImpl
import com.vp.weatherapp.ui.main.paging.PagingActivity
import org.koin.android.ext.android.inject


class MainActivity : PagingActivity(), MainContract.View {

    private val BACKGROUND_COLORS = intArrayOf(-0xcfb002, -0x33ff9a, -0x66ff01)

    override val presenter: MainContract.Presenter by inject { mapOf(MAIN_VIEW to this) }

    override fun generatePages(savedInstanceState: Bundle?): List<Fragment> {
        val pages = arrayListOf<Fragment>()

        for (i in 0 until BACKGROUND_COLORS.size) {
            val newPage = WeatherFragment.newInstance()
//            newPage.setFrontImage(frontDots)
//            newPage.setBackImage(backDots)
            pages.add(newPage)
        }

        return pages
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkDatabaseInitialized()

        presenter.getSelectedCities()
        configureBackground()
    }

    private fun checkDatabaseInitialized() {
        val prefs = getSharedPreferences(PREFS_FILENAME, 0)
        val dbInitialized = prefs.getBoolean(DATABASE_INITIALIZED, false)
        if (!dbInitialized) {
            startActivity(InitialActivity.newIntent(this))
            finish()
        }
    }

    override fun buildFragments(selectedCities: List<String>) {
        configureBackground()
    }

    private fun configureBackground() {
        backgroundManager = BackgroundManagerImpl(BACKGROUND_COLORS)
    }

    override fun onStop() {
        presenter.stop()
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
//        repo.getHourlyForecast("Stockholm", "SE")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        { forecasts -> printForecasts(forecasts) },
//                        { throwable -> handleError(throwable) } )
    }

    companion object {
        const val PREFS_FILENAME = "com.vp.weather.prefs"
        const val DATABASE_INITIALIZED = "DATABASE_INITIALIZED"

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }


}
