package com.ivy.weatherapp.ui

import android.app.Activity
import android.os.Bundle
import com.ivy.weatherapp.R

class WeatherActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
