package sample.network.rahul.android_weather_app

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
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
import sample.network.rahul.android_weather_app.gps.GPSTracker

class WeatherActivity : AppCompatActivity() {
    companion object {
        internal val REQUEST_LOCATION = 199
    }

    private var gps: GPSTracker? = null
    private var googleApiClient: GoogleApiClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        text.setOnClickListener { callLocation() }
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
        if(gps == null) {
            gps = GPSTracker(this@WeatherActivity)
        }
        gps!!.getLocation()
        // check if GPS enabled
        if (gps!!.canGetLocation()) {
                gps!!.location.observe(this, Observer {t->
                    if(t!=null) {
                        val latitude = t!!.getLatitude()
                        val longitude = t!!.getLongitude()
                        Log.e("MainActivity", "latitude -> $latitude")
                        Log.e("MainActivity", "longitude -> $longitude")
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