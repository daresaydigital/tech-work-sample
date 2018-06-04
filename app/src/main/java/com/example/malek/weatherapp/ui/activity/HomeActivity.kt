package com.example.malek.weatherapp.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.malek.weatherapp.R
import com.example.malek.weatherapp.models.CurrentWeather
import com.example.malek.weatherapp.ui.viewModels.HomeViewModel
import com.example.malek.weatherapp.ui.viewModels.HomeViewModelImp
import com.example.malek.weatherapp.utils.AndroidUtils
import com.example.malek.weatherapp.utils.TimeUtils
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.squareup.picasso.Picasso
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {


    val TAG = HomeActivity::class.java.simpleName
    var fusedLocationClient: FusedLocationProviderClient? = null
    var snackbar: Snackbar? = null
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    var gettingWeather = true
    var disposable: Disposable? = null
    val homeViewModel: HomeViewModel = HomeViewModelImp()

    companion object {
        val URL_ICONS = "http://openweathermap.org/img/w/"
        val REQUEST_CHECK_SETTINGS = 1

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RxPermissions(this)
                .request(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe({
                    if (it) {
                        //check location settings
                        locationRequest = createLocationRequest()
                        locationCallback = createLoctionCallback()
                        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                        val builder = LocationSettingsRequest.Builder()
                                .addLocationRequest(locationRequest)
                        val client: SettingsClient = LocationServices.getSettingsClient(this)
                        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
                        // location service is enable
                        task.addOnSuccessListener { locationSettingsResponse ->
                            //get location of the user
                            startLocationUpdates()
                        }
                        // location service is not  enable

                        task.addOnFailureListener {
                            if (it is ResolvableApiException) {
                                try {
                                    it.startResolutionForResult(this,
                                            REQUEST_CHECK_SETTINGS)
                                } catch (sendEx: IntentSender.SendIntentException) {
                                    //get the last location in case of error
                                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                        fusedLocationClient?.lastLocation
                                                ?.addOnSuccessListener { location: Location? ->
                                                    Log.e(TAG, location.toString())
                                                    location?.let {
                                                        getWeatherByLocation(it)
                                                    }

                                                }
                                    } else {
                                        onError()
                                    }
                                }
                            }
                        }
                        swipeRefreshLayout.isEnabled = false
                        swipeRefreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
                            override fun onRefresh() {
                                if (!gettingWeather && AndroidUtils.isConnected(this@HomeActivity)) {
                                    startLocationUpdates()
                                } else {
                                    swipeRefreshLayout.isRefreshing = false
                                }
                            }

                        })

                    } else {
                        onError()
                    }
                }, {
                    Log.e(TAG, it.toString())
                    onError()
                })

    }

    private fun stopLocationUpdates() {
        if (::locationCallback.isInitialized) {
            fusedLocationClient?.removeLocationUpdates(locationCallback)
        }
    }

    fun createLocationRequest(): LocationRequest = LocationRequest().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun createLoctionCallback(): LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            gettingWeather = false
            swipeRefreshLayout?.isRefreshing = false
            swipeRefreshLayout?.isEnabled = true
            locationResult ?: return
            for (location in locationResult.locations) {
                Log.e(TAG, location.toString())
                getWeatherByLocation(location)
            }
            stopLocationUpdates()
        }
    }

    private fun startLocationUpdates() {
        if (::locationRequest.isInitialized && ::locationCallback.isInitialized) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                fusedLocationClient?.requestLocationUpdates(locationRequest,
                        locationCallback,
                        null)
            }
        }
    }

    // after the user enable location services
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == Activity.RESULT_OK) {
            startLocationUpdates()

        }
    }


    @SuppressLint("SetTextI18n")
    fun getWeatherByLocation(location: Location) {
        disposable = homeViewModel.getCurrentWeatherObservable(location, this@HomeActivity)
                .subscribe({
                    if (it != null) {
                        setCurrentWeatherUi(it)
                    } else {
                        onError()
                    }
                }, {
                    progress.visibility = View.GONE
                    Log.e(TAG, it.toString())
                    onError()

                })
    }

    @SuppressLint("SetTextI18n")
    private fun setCurrentWeatherUi(currentWeather: CurrentWeather) {
        progress.visibility = View.GONE
        root.visibility = View.VISIBLE

        name.text = currentWeather.name
        temp.text = "${currentWeather.main?.temp?.toInt()}°"
        currentWeather.weather?.get(0)?.let {
            mainWeather.text = it.main
            description.text = it.description
            Picasso.with(this@HomeActivity)
                    .load(URL_ICONS + it.icon + ".png")
                    .fit()
                    .into(icon)

        }
        currentWeather.sys?.sunrise?.let {
            sunRiseHeader.visibility = View.VISIBLE
            sunRise.visibility = View.VISIBLE
            sunRise.text = TimeUtils.getTimeFromTimeStamp(it, TimeUtils.hourFormat)
        }
        currentWeather.sys?.sunset?.let {
            sunSetHeader.visibility = View.VISIBLE
            sunSet.visibility = View.VISIBLE
            sunSet.text = TimeUtils.getTimeFromTimeStamp(it, TimeUtils.hourFormat)
        }
        currentWeather.wind?.speed?.let {
            windSpeedHeader.visibility = View.VISIBLE
            windSpeed.visibility = View.VISIBLE
            windSpeed.text = "${it.toInt()} mps"

        }
        currentWeather.wind?.deg?.let {
            windDegHeader.visibility = View.VISIBLE
            windDeg.visibility = View.VISIBLE
            windDeg.text = it.toString()

        }
        if (currentWeather.clouds != null) {
            clouds.visibility = View.VISIBLE
            cloudsHeader.visibility = View.VISIBLE
            clouds.text = currentWeather.clouds.all.toString()
        }
        currentWeather.main?.let {
            if (it.humidity != null) {
                humidityHeader.visibility = View.VISIBLE
                humidity.visibility = View.VISIBLE
                humidity.text = "${it.humidity}%"
            }
            if (it.pressure != null) {
                pressureHeader.visibility = View.VISIBLE
                pressure.visibility = View.VISIBLE
                pressure.text = "${it.pressure} hPa"
            }
            if (it.tempMax != null && it.tempMin != null) {
                tempDay.text = "${it.tempMin.toInt()}°/${it.tempMax.toInt()}°"
            }

        }
        currentWeather.dt?.let {
            lastUpdate.text = "${getString(R.string.last_update)} ${TimeUtils.getTimeFromTimeStamp(it, TimeUtils.dayFormat)}"
        }
    }


    private fun onError() {
        progress.visibility = View.GONE
        if (snackbar == null) {
            snackbar = Snackbar.make(container, R.string.error, Snackbar.LENGTH_LONG)

        }
        snackbar?.show()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (::locationCallback.isInitialized) {
            stopLocationUpdates()
        }
        disposable?.dispose()

    }
}
