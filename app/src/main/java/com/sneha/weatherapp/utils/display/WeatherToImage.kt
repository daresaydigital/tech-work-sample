package com.sneha.weatherapp.utils.display

import com.sneha.weatherapp.R

object WeatherToImage {
    fun getImageForCode(code: String): Int = when (code) {
        "01d" -> {
            R.drawable.ic_clear_sky
        }
        "01n" -> {
            R.drawable.ic_clear_sky_night
        }
        "02d", "02n" -> {
            R.drawable.ic_few_clouds
        }
        "03d", "03n" -> {
            R.drawable.ic_broken_cloud
        }
        "04d", "04n" -> {
            R.drawable.ic_broken_cloud
        }
        "09d", "09n" -> {
            R.drawable.ic_shower_rain
        }
        "10d", "10n" -> {
            R.drawable.ic_rain
        }
        "11d", "11n" -> {
            R.drawable.ic_thunderstorm
        }
        "13d", "13n" -> {
            R.drawable.ic_snow
        }
        "50d", "50n" -> {
            R.drawable.ic_mist
        }
        else -> {
            R.drawable.ic_broken_cloud
        }
    }
}