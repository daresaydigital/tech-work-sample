package com.deresay.sayweather.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.daresay.sayweather.R

class SplashActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler = Handler()
    }


    override fun onResume() {
        super.onResume()
        runnable = showWeather()
        handler.postDelayed(runnable, 1000)
    }


    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    /**
     * Load [ShowWeatherActivity]
     */
    private fun showWeather() = Runnable {
        startActivity(Intent(this,ShowWeatherActivity::class.java))
        finish()
    }

}
