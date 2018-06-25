package com.ukhanoff.rainbeforeseven

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import com.ukhanoff.rainbeforeseven.fragment.WeatherFragment
import dagger.android.support.DaggerAppCompatActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class WeatherActivity : DaggerAppCompatActivity() {

    lateinit var weatherFragment: WeatherFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_activity)
        showFragment()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun showFragment() {
        weatherFragment = WeatherFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, weatherFragment, null)
                .commit()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            weatherFragment?.triggerWeatherUpdate()
        } else {
            Toast.makeText(this, getString(R.string.no_permission_alert), Toast.LENGTH_LONG).show()
        }
    }
}
