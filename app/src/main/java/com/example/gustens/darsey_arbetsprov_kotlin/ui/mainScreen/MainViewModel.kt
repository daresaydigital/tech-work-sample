package com.example.gustens.darsey_arbetsprov_kotlin.ui.mainScreen

import android.Manifest
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.support.v4.content.ContextCompat
import android.util.Log
import com.example.domain.model.DailyForecastResponse
import com.example.domain.model.ForecastResponse
import com.example.domain.model.Main
import com.example.domain.model.WeatherResponse
import com.example.gustens.darsey_arbetsprov_kotlin.app.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlin.math.roundToInt


class MainViewModel(application: Application): AndroidViewModel(application), MainContract.ViewModel, MainContract.InteractorOutput
{

    private val LOG_TAG = MainViewModel::class.java.name

    private var mView: MainContract.View? = null
    private val mInteractor: MainContract.Interactor = MainInteractor(application, this)
    private var mIntialized: Boolean = false;
    private val mApplication: Application = application

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationCallback: LocationCallback
    private lateinit var mLocationRequest: LocationRequest

    private var mWeatherResponse : WeatherResponse? = null
    private var mForecastResponse: ForecastResponse? = null
    private var mDailyForecastResponse : DailyForecastResponse? = null
    private var windDirectionOld : Float = 0.0f


    private var mAddressOld : Address? = null

    override fun init(view: MainContract.View, fusedLocationClient: FusedLocationProviderClient) {

        mView = view;
        mFusedLocationClient = fusedLocationClient

        mLocationRequest = LocationRequest()
        mLocationRequest.interval = Constants.FUSED_LOCATION_INTERVALL
        mLocationRequest.fastestInterval = Constants.FUSED_LOCATION_FASTEST_INTERVALL
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                Log.e(LOG_TAG, "onLocationResult: " + locationResult.locations.size)
                if(locationResult.locations.size > 0) {

                    mInteractor.getWeatherDataByCoord(locationResult.lastLocation.latitude.toString(),locationResult.lastLocation.longitude.toString())
                    mInteractor.getForecastByCoord(locationResult.lastLocation.latitude.toString(),locationResult.lastLocation.longitude.toString())
                    // mInteractor.getDailyForecastByCoord(locationResult.lastLocation.latitude.toString(),locationResult.lastLocation.longitude.toString())
                    mInteractor.getLocation(locationResult.lastLocation,mApplication)

                }
            }
        }
    }

    override fun onResume() {
        Log.e(LOG_TAG, "onResume")
        startLocationUpdates()
    }

    override fun onPause() {
        Log.e(LOG_TAG, "onPause")
        stopLocationUpdates()
    }

    override fun startLocationUpdates() {

        if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            mView!!.shouldShowPermission()

        } else {

            Log.e(LOG_TAG, "Permission is granted")

            // Only get last location if not initialized
            if(!mIntialized) {

                Log.e(LOG_TAG, "---- INIT RUN ----")

                mIntialized = true;

                mFusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            mInteractor.getLocation(location!!, mApplication)
                        }
            } else {

                Log.e(LOG_TAG, "---- INIT USE VIEW MODEL ----")

                if (mWeatherResponse != null)
                    onWeatherDataRecieved(mWeatherResponse!!)


                if (mForecastResponse != null) {
                    onForecastDataRecieved(mForecastResponse!!)
                }

            }


            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback,
                    null /* Looper */)
        }
    }

    private fun stopLocationUpdates() {

            mFusedLocationClient.removeLocationUpdates(mLocationCallback)

    }

    override fun onAdressRecieved(addressList: List<Address>) {
        var adressNew: Address? = null
        for (adress in addressList){
            adressNew = adress
        }
            mAddressOld = adressNew

        if (adressNew != null) {

            mView!!.setAdress(adressNew)
        }

    }

    override fun onWeatherDataRecieved(weatherResponse: WeatherResponse) {
        Log.e(LOG_TAG, "onWeatherDataRecieved: ")
        mWeatherResponse = weatherResponse
        updateView(mWeatherResponse!!)

    }


    private fun updateView(weatherResponse: WeatherResponse){
        mView!!.setweatherIcon(weatherResponse.weather!![0].icon!!)

        mView!!.setWeather(weatherResponse.weather!![0])

        if(weatherResponse.wind!!.deg != null) {
            val windDirectionNew = weatherResponse.wind!!.deg!!.toFloat()
            mView!!.setWind(weatherResponse.wind!!.speed!!, windDirectionOld, windDirectionNew)
            windDirectionOld = windDirectionNew
        }

        mView!!.setMainData(weatherResponse.main!!)

        setDewPoint(weatherResponse.main!!)

    }

    override fun onForecastDataRecieved(forecastResponse: ForecastResponse) {
        Log.e(LOG_TAG, "onHourlyForecast recieved: ")
        mForecastResponse = forecastResponse
        mView!!.setForecastAdapter(forecastResponse.list!!)

    }


    override fun onDailyForecastDataRecieved(forecastResponse: DailyForecastResponse) {
        Log.e(LOG_TAG, "onDailyForecast recieved: ")
        mDailyForecastResponse = forecastResponse
        // mView!!.setForecastAdapter(forecastResponse.list!!)
    }


    private fun setDewPoint(main : Main){

        val dewPoint : Int = calculateDewPoint(main.temp!!.toFloat(),main.humidity!!.toFloat() )

        mView!!.setDewPoint(dewPoint.toString()+"C")
    }


    private fun calculateDewPoint(temperature: Float, relativeHumidity: Float): Int {
        val m = 17.62f
        val Tn = 243.12f

        val Td:Int = (Tn * ((Math.log((relativeHumidity / 100).toDouble()) + m * temperature / (Tn + temperature)) / (m - (Math.log((relativeHumidity / 100).toDouble()) + m * temperature / (Tn + temperature))))).toFloat().roundToInt()

        return Td
    }


    override fun onCleared() {
        super.onCleared()
        Log.e(LOG_TAG, "onCleared()")

        // Clean up background threads
        mInteractor.cleanUp()

    }
}