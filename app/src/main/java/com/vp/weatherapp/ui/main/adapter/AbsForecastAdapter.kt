package com.vp.weatherapp.ui.main.adapter

import android.support.v7.widget.RecyclerView
import com.vp.weatherapp.R


abstract class AbsForecastAdapter<VH : RecyclerView.ViewHolder>
    : RecyclerView.Adapter<VH>() {

    fun getConditionDrawableByIcon(icon: String): Int {
        return when (icon) {
            "01d" -> R.drawable.wi_day_sunny
            "01n" -> R.drawable.wi_night_clear
            "02d" -> R.drawable.wi_day_cloudy
            "02n" -> R.drawable.wi_night_cloudy
            "03d" -> R.drawable.wi_day_cloudy
            "03n" -> R.drawable.wi_night_cloudy
            "04d" -> R.drawable.wi_day_cloudy
            "04n" -> R.drawable.wi_night_cloudy
            "09d" -> R.drawable.wi_day_showers
            "09n" -> R.drawable.wi_night_showers
            "10d" -> R.drawable.wi_day_rain
            "10n" -> R.drawable.wi_night_rain
            "11d" -> R.drawable.wi_day_thunderstorm
            "11n" -> R.drawable.wi_night_thunderstorm
            "13d" -> R.drawable.wi_day_snow
            "13n" -> R.drawable.wi_night_snow
            "50d" -> R.drawable.wi_day_fog
            "50n" -> R.drawable.wi_day_fog
            else -> R.drawable.wi_unknown
        }
    }
}