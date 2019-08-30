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
import androidx.viewpager.widget.ViewPager
import com.example.weatherapp.R
import com.example.weatherapp.ui.CityAddActivity.Companion.CITY_ID_EXTRA
import com.example.weatherapp.utils.My_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION
import com.example.weatherapp.viewModels.HomeViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel


fun Double.fromKelvinToDegree() = (this - 273.15).toInt()

class WeatherActivity : AppCompatActivity() {

    val homeViewModel: HomeViewModel by viewModel()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var cityWeatherAdapter: CityWeatherViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        preparePager()

        subscribeUi()
        attachHandlers()
    }

    private fun preparePager() {
        cityWeatherAdapter = CityWeatherViewPagerAdapter(supportFragmentManager)
        cityPager.adapter = cityWeatherAdapter
        cityPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                homeViewModel.select(position)
            }
        })
    }

    private fun subscribeUi() {
        homeViewModel.selectedCity.observe(this, Observer {
            it?.let {
                cityOrLocation.text = it.name
            }

        })

        homeViewModel.listOfCityWithWeather.observe(this, Observer { citiesWithWeather ->
            if (citiesWithWeather != null) {
                if (citiesWithWeather.isNotEmpty()) {
                    if (dataAvailable.visibility == View.GONE)
                        homeViewModel.select(0)

                    dataAvailable.visibility = View.VISIBLE
                    noDataAvailable.visibility = View.GONE
                    cityWeatherAdapter.update(citiesWithWeather)

                }
            } else {
                dataAvailable.visibility = View.GONE
                noDataAvailable.visibility = View.VISIBLE
            }

        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            My_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    detectLocation()
                }
            }
        }
    }

    private fun attachHandlers() {
        cityBtn.setOnClickListener {
            val i = Intent(this, CityAddActivity::class.java)
            startActivityForResult(i, REQUEST_ADD_CITY)
        }

        detectLocation.setOnClickListener {
            detectLocation()
        }
    }


    private fun detectLocation() {
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
                fusedLocationProviderClient.lastLocation?.addOnSuccessListener {
                    if (it != null) {
                        homeViewModel.fetchWeatherForLocation(it.latitude, it.longitude)
                    }
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD_CITY) {
            val cityId = data?.getLongExtra(CITY_ID_EXTRA, -1)
            if (cityId != null && cityId != -1L) {
                homeViewModel.fetchUsingCityId(cityId)
            }
        }
    }

    val REQUEST_ADD_CITY = 100

}