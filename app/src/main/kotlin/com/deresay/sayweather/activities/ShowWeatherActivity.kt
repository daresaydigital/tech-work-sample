package com.deresay.sayweather.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.daresay.sayweather.R
import com.deresay.sayweather.fragments.ShowWeatherFragment
import com.deresay.sayweather.utils.FragmentUtils

class ShowWeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_weather)
        showWeather()
    }

    /**
     * The initial home screen that displays basic weather info from API.
     */
    private fun showWeather() {
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, ShowWeatherFragment.newInstance())
                .commit()
    }

}
