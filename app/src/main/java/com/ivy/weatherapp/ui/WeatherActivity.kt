package com.ivy.weatherapp.ui

import android.os.Bundle
import com.ivy.weatherapp.ui.base.BaseActivity

class WeatherActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, WeatherFragment())
                    .commit()
        }
    }
}
