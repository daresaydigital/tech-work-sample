package sample.network.rahul.android_weather_app.gps

import android.app.AlertDialog
import android.app.Service
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log

class GPSTracker(private val mContext: Context) : Service(), LocationListener {

    // flag for GPS status
    internal var isGPSEnabled = false

    // flag for network status
    internal var isNetworkEnabled = false

    // flag for GPS status
    internal var canGetLocation = false

    //internal var location: Location? = null // location
    var location: MutableLiveData<Location> = MutableLiveData()
    internal var latitude: Double = 0.toDouble() // latitude
    internal var longitude: Double = 0.toDouble() // longitude

    // Declaring a Location Manager
    protected var locationManager: LocationManager? = null

    init {
        locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this)
    }

    fun getLocation(): Location? {
        try {
            // getting GPS status
            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

            // getting network status
            isNetworkEnabled = locationManager!!
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true

                //First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager!!.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this)

                    Log.d("Network", "Network")
                    if (locationManager != null) {
                        location.value = locationManager!!
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                        if (location.value != null) {
                            latitude = location.value!!.latitude
                            longitude = location.value!!.longitude
                        }
                    }
                }

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled && location.value == null) {


                    Log.d("GPS Enabled", "GPS Enabled")
                    if (locationManager != null) {
                        location.value = locationManager!!
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER)

                    }

                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return location.value
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */

    fun stopUsingGPS() {
        if (locationManager != null) {
            locationManager!!.removeUpdates(this@GPSTracker)
        }
    }

    /**
     * Function to get latitude
     */

    fun getLatitude(): Double {
        if (location.value != null) {
            latitude = location.value!!.latitude
        }

        // return latitude
        return latitude
    }

    /**
     * Function to get longitude
     */

    fun getLongitude(): Double {
        if (location.value != null) {
            longitude = location.value!!.longitude
        }

        // return longitude
        return longitude
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */

    fun canGetLocation(): Boolean {
        return this.canGetLocation
    }


    override fun onLocationChanged(location: Location) {

        this.location.value = location
    }

    override fun onProviderDisabled(provider: String) {}

    override fun onProviderEnabled(provider: String) {}

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    companion object {

        // The minimum distance to change Updates in meters
        private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters

        // The minimum time between updates in milliseconds
        private val MIN_TIME_BW_UPDATES = (1000 * 60 * 1).toLong() // 1 minute
    }
}