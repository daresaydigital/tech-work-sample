package com.vp.weatherapp.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.vp.weatherapp.R
import com.vp.weatherapp.data.local.entity.CityWithForecast
import com.vp.weatherapp.ui.main.paging.BackgroundManagerImpl
import com.vp.weatherapp.ui.main.paging.PagingActivity


class MainActivity : PagingActivity() {


    override fun generatePages(selectedCities: List<CityWithForecast>): List<Fragment> {
        var backgroundColors = listOf(Color.BLACK)
        var pages = listOf(NoCitySelectedFragment.newInstance())

        if (selectedCities.isNotEmpty()) {
            backgroundColors = selectedCities.map { ContextCompat.getColor(this, getWeatherColor(it.icon)) }
            pages = selectedCities.map { WeatherFragment.newInstance(it) }
        }

        configureBackground(backgroundColors)

        return pages
    }

    private fun getWeatherColor(icon: String?) : Int {
        return when (icon) {
            "01d" -> R.color.weather_color_clear
            "01n" -> R.color.weather_color_clear_night
            "02d" -> R.color.weather_color_cloudy
            "02n" -> R.color.weather_color_cloudy_night
            "03d" -> R.color.weather_color_cloudy
            "03n" -> R.color.weather_color_cloudy_night
            "04d" -> R.color.weather_color_cloudy
            "04n" -> R.color.weather_color_cloudy_night
            "09d" -> R.color.weather_color_stormy
            "09n" -> R.color.weather_color_stormy_night
            "10d" -> R.color.weather_color_stormy
            "10n" -> R.color.weather_color_stormy_night
            "11d" -> R.color.weather_color_stormy
            "11n" -> R.color.weather_color_stormy_night
            "13d" -> R.color.weather_color_cloudy
            "13n" -> R.color.weather_color_cloudy_night
            "50d" -> R.color.weather_color_fog
            "50n" -> R.color.weather_color_fog_night
            else -> R.color.solid_black
        }
    }

    private fun configureBackground(colors: List<Int>) {
        backgroundManager = BackgroundManagerImpl(colors)
    }


    companion object {
        const val PREFS_FILENAME = "com.vp.weather.prefs"
        const val DATABASE_INITIALIZED = "DATABASE_INITIALIZED"

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

}
