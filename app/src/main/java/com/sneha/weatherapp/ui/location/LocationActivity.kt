package com.sneha.weatherapp.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sneha.weatherapp.R
import com.sneha.weatherapp.ui.weather.WeatherActivity
import com.sneha.weatherapp.utils.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_location.*


const val LOCATION_REQUEST = 100

class LocationActivity : AppCompatActivity() {

    lateinit var locationViewModel: LocationViewModel

    private var isGPSEnabled = false

    private val gpsObserver = Observer<GpsStatus> { status ->
        status?.let {
            when (status) {
                is GpsStatus.Enabled -> {
                    isGPSEnabled = true
                    invokeLocationAction()
                }

                is GpsStatus.Disabled -> {
                    isGPSEnabled = false
                    invokeLocationAction()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        tv_locate.setOnClickListener {
            when (tv_locate.text) {
                getString(R.string.locate) -> ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    LOCATION_REQUEST
                )
                getString(R.string.enable_gps) -> {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
                else -> {
                    //DO NOTHING
                }
            }
        }
    }

    private fun startLocationUpdate() {
        locationViewModel.getLocationData().observe(this, Observer {
            //            latLong.text = getString(R.string.latLong, it.longitude, it.latitude)
            latLong.text = getString(R.string.loading_data)
            finish()
            startActivity(Intent(applicationContext, WeatherActivity::class.java))
        })
    }

    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED


    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }

    private fun invokeLocationAction() {
        when {
            !isGPSEnabled -> {
                tv_locate_city.text = getString(R.string.turn_gps_on)
                tv_locate.text = getString(R.string.enable_gps)
                latLong.text = getString(R.string.enable_gps_message)
            }

            isPermissionsGranted() -> startLocationUpdate()

            shouldShowRequestPermissionRationale() -> {
                tv_locate_city.text = getString(R.string.locate_current_city)
                tv_locate.text = getString(R.string.locate)
                latLong.text = getString(R.string.permission_request)
            }

            else -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    LOCATION_REQUEST
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        locationViewModel = ViewModelProviders.of(
            this, ViewModelProviderFactory(LocationViewModel::class) {
                LocationViewModel(application)
            }).get(LocationViewModel::class.java)
        locationViewModel.gpsStatusLiveData.observe(this, gpsObserver)

        invokeLocationAction()
    }
}


