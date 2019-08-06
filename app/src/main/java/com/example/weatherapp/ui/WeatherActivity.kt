package com.example.weatherapp.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.weatherapp.R
import com.example.weatherapp.ui.CityAddActivity.Companion.CITY_ID_EXTRA
import com.example.weatherapp.utils.My_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION
import com.example.weatherapp.viewModels.WeatherViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.android.viewmodel.ext.android.viewModel


fun Double.fromKelvinToDegree() = (this - 273.15).toInt()

class WeatherActivity : AppCompatActivity() {

    val weatherViewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        subscribeUi()
        attachHandlers()

        // todo : for now check for location & query
        checkForLocationPermission()
    }

    private fun subscribeUi() {
        weatherViewModel.currentWeather.observe(this, Observer {
            it?.let {
                dataAvailable.visibility = View.VISIBLE



                cityOrLocation.text = it.name
                currentTemp.text = "${it.main.temp.fromKelvinToDegree()}\u00B0"

                minTemp.text = "${it.main.temp_min.fromKelvinToDegree()}\u00B0"
                maxTemp.text = "${it.main.temp_max.fromKelvinToDegree()}\u00B0"

                with(it.weather.first()) {
                    val moodColor = when {
                        description.contains("rains", true) -> R.color.weather_average
                        description.contains("storm", true) -> R.color.weather_bad
                        else -> R.color.weather_good
                    }

                    window.statusBarColor = ContextCompat.getColor(this@WeatherActivity, moodColor)
                    root.setBackgroundColor(ContextCompat.getColor(this@WeatherActivity, moodColor))

                    weatherStatus.text = description
                    Picasso.get().load("http://openweathermap.org/img/wn/$icon@2x.png").into(currentWeatherIcon);
                }
            }
        })
    }

    private fun checkForLocationPermission() {
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(application.applicationContext) == ConnectionResult.SUCCESS) {
            if (ContextCompat.checkSelfPermission(
                    application.applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    My_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION
                )
            } else {
                weatherViewModel.updateWithDetectLocation()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            My_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    weatherViewModel.updateWithDetectLocation()
                }
            }
        }
    }

    private fun attachHandlers() {
        cityBtn.setOnClickListener {
            val i = Intent(this, CityAddActivity::class.java)
            startActivityForResult(i, REQUEST_ADD_CITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode  == REQUEST_ADD_CITY) {
            val cityId = data?.getLongExtra(CITY_ID_EXTRA, -1)
            if (cityId != null && cityId != -1L) {
                weatherViewModel.updateWithCity(cityId)
            }
        }
    }

    val REQUEST_ADD_CITY = 100

}