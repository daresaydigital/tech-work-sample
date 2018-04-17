package com.baryshev.dmitriy.daresayweather.main.domain

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.baryshev.dmitriy.daresayweather.R

/**
 * 4/17/2018.
 */
enum class WeatherCondition(@DrawableRes val icon: Int, @StringRes val advice: Int) {
    CLEAR_DAY(R.drawable.ic_clear_day, R.string.advice_sunny),
    CLEAR_NIGHT(R.drawable.ic_clear_night, R.string.advice_moon),
    FEW_CLOUD_DAY(R.drawable.ic_cloudy_day, R.string.advice_day_few_cloud),
    FEW_CLOUD_NIGHT(R.drawable.ic_cloudy_night, R.string.advice_night_few_cloud),
    CLOUD_DAY(R.drawable.ic_cloudy_day, R.string.advice_day_cloud),
    CLOUD_NIGHT(R.drawable.ic_cloudy_night, R.string.advice_night_cloud),
    BROKEN_CLOUD_DAY(R.drawable.ic_broken_clouds, R.string.advice_day_broken_cloud),
    BROKEN_CLOUD_NIGHT(R.drawable.ic_broken_clouds, R.string.advice_night_broken_cloud),
    RAIN_DAY(R.drawable.ic_rainy_day, R.string.advice_day_rain),
    RAIN_NIGHT(R.drawable.ic_rainy_night, R.string.advice_night_rain),
    SHOWER_RAIN_DAY(R.drawable.ic_rainy_day, R.string.advice_day_shower),
    SHOWER_RAIN_NIGHT(R.drawable.ic_rainy_night, R.string.advice_night_shower),
    THUNDER_DAY(R.drawable.ic_thunder, R.string.advice_day_thunder),
    THUNDER_NIGHT(R.drawable.ic_thunder, R.string.advice_night_thunder),
    SNOW_DAY(R.drawable.ic_snow, R.string.advice_day_snow),
    SNOW_NIGHT(R.drawable.ic_snow, R.string.advice_night_snow),
    FOG_DAY(R.drawable.ic_foggy, R.string.advice_day_fog),
    FOG_NIGHT(R.drawable.ic_foggy, R.string.advice_night_fog),
    UNKNOWN(R.drawable.ic_unknown, R.string.advice_unknown)
}