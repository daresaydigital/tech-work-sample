package com.sneha.weatherapp.ui.weather

import android.os.Bundle
import com.sneha.weatherapp.R
import com.sneha.weatherapp.di.component.ActivityComponent
import com.sneha.weatherapp.ui.base.BaseActivity
import com.sneha.weatherapp.utils.common.TimeUtils
import com.sneha.weatherapp.utils.display.WeatherToImage
import kotlinx.android.synthetic.main.activity_selected_day.*
import kotlinx.android.synthetic.main.activity_selected_day.tv_day
import kotlinx.android.synthetic.main.activity_selected_day.tv_feels_like
import com.google.gson.Gson
import com.sneha.weatherapp.data.model.Forecast


class SelectedDayForecastActivity : BaseActivity<WeatherViewModel>() {

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {

        Gson().fromJson(
            intent.getStringExtra("forecast_data"),
            Forecast::class.java
        ).let {
            tv_day.text = TimeUtils.getTimeInExpectedFormat(it.dt, "EEEE")
            tv_feels_like.text = it.weather[0].description.capitalize()
            tv_icon.setImageResource(WeatherToImage.getImageForCode(it.weather[0].icon))
            rv_temp_min_max.text = getString(R.string.temperature).plus(" ")
                .plus(it.temp.min.toInt()).plus("℃").plus("/")
                .plus(it.temp.max.toInt()).plus("℃")
            tv_humidity.text = it.humidity.toString().plus("%")
            tv_pressure.text = it.pressure.toString().plus(" hpa")
            tv_sunrise.text = TimeUtils.getTimeInExpectedFormat(it.sunrise, "HH:mm")
            tv_sunset.text = TimeUtils.getTimeInExpectedFormat(it.sunset, "HH:mm")
        }

        cancel.setOnClickListener { finish() }
    }

    override fun provideLayoutId(): Int = R.layout.activity_selected_day
}