package sample.network.rahul.android_weather_app

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import kotlinx.android.synthetic.main.activity_main.*
import sample.network.rahul.android_weather_app.datasource.data.WeatherResponse
import sample.network.rahul.android_weather_app.gps.GPSTracker
import sample.network.rahul.android_weather_app.util.Utils
import sample.network.rahul.android_weather_app.viewmodel.WeatherViewModel

class WeatherActivity : AppCompatActivity() {
    companion object {
        internal val REQUEST_LOCATION = 199
    }

    private var gps: GPSTracker? = null
    private var googleApiClient: GoogleApiClient? = null
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        weatherViewModel.getWeather().observe(this, Observer { weatherResponse ->
            run {
                updateUISuccess(weatherResponse)
            }
        })
    }

    fun updateUISuccess(weatherResponse: WeatherResponse?) {
        if (weatherResponse != null) {
            weatherTextView.text = weatherResponse.weather?.get(0)?.description ?: "Clear Sky"
            cityTextView.text = weatherResponse.name ?: "--"
            tempTextView.text = resources.getString(R.string.temp, weatherResponse.main?.temp?.toInt().toString())
            sunRiseTextView.text = Utils.millisecondToTime(weatherResponse.sys?.sunrise ?: 0)
            sunSetTextView.text = Utils.millisecondToTime(weatherResponse.sys?.sunset ?: 0)
            windTextView.text = String.format("%.2f", weatherResponse.wind?.speed ?: 0.0) + " km/h"
            humidityTextView.text = weatherResponse.main?.humidity?.toInt().toString() + "%"
            updateWeatherIcon(weatherResponse.weather?.get(0)?.icon)
        }
    }

    private fun updateWeatherIcon(icon: String?) {
        var drawable: Int
        when(icon){
            "01d" -> drawable = R.drawable.ic_sunny_2
            "01n" -> drawable = R.drawable.ic_moon
            "02d" -> drawable = R.drawable.ic_few_clouds_day
            "02n" -> drawable = R.drawable.ic_few_clouds_night
            "03d","03n" -> drawable = R.drawable.ic_scattered_clouds
            "04d","04n" -> drawable = R.drawable.ic_broken_clouds
            "09d","09n" -> drawable = R.drawable.ic_shower_rain
            "010d" -> drawable = R.drawable.ic_rain_day
            "010n" -> drawable = R.drawable.ic_rain_night
            "11d","11n" -> drawable = R.drawable.ic_thunderstorm
            "13d","13n" -> drawable = R.drawable.ic_snow
            "50d","50n" -> drawable = R.drawable.ic_mist
            else -> drawable = R.drawable.ic_sunny_2
        }
        weatherImageView.setImageResource(drawable)
    }

    private fun enableLoc() {
        if (googleApiClient == null) {
            googleApiClient = GoogleApiClient.Builder(this@WeatherActivity)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                        override fun onConnected(bundle: Bundle?) {

                        }

                        override fun onConnectionSuspended(i: Int) {
                            googleApiClient!!.connect()
                        }
                    })
                    .addOnConnectionFailedListener { connectionResult -> Log.d("Location error", "Location error " + connectionResult.errorCode) }.build()
            googleApiClient!!.connect()
        }
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = (30 * 1000).toLong()
        locationRequest.fastestInterval = (5 * 1000).toLong()
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

        builder.setAlwaysShow(true)

        val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status = result.status

            Log.e("Location", "status Called  -->" + status.statusCode)

            when (status.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    Log.e("Location", "LocationSettingsStatusCodes.RESOLUTION_REQUIRED Called ....")
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult(this@WeatherActivity, REQUEST_LOCATION)
                    } catch (e: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }

                }
            }
        }

    }


    private fun callLocation() {
        Log.e("Location", "callLocation Called ... ")
        if (gps == null) {
            gps = GPSTracker(this@WeatherActivity)
        }
        gps!!.getLocation()
        // check if GPS enabled
        if (gps!!.canGetLocation()) {
            gps!!.location.observe(this, Observer { t ->
                if (t != null) {
                    val latitude = t.getLatitude()
                    val longitude = t.getLongitude()
                    Log.e("MainActivity", "latitude -> $latitude")
                    Log.e("MainActivity", "longitude -> $longitude")
                    weatherViewModel.reFetchWeather(t)
                }
            })


        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            enableLoc()
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionCheck = ContextCompat.checkSelfPermission(this@WeatherActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                Log.e("permission", "granted")
                callLocation()
            } else {
                ActivityCompat.requestPermissions(this@WeatherActivity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            }
        } else {
            Log.e("MainActivity", "Lower Than MarshMallow")
            callLocation()
        }
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            REQUEST_LOCATION -> when (resultCode) {
                Activity.RESULT_OK -> {
                    // All required changes were successfully made
                    callLocation()

                    Toast.makeText(this@WeatherActivity, "Location enabled by user!", Toast.LENGTH_LONG).show()
                }
                Activity.RESULT_CANCELED -> {
                    // The user was asked to change settings, but chose not to
                    Toast.makeText(this@WeatherActivity, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show()
                    finish()
                }
                else -> {
                }
            }
        }
    }


}