package se.bynumbers.daresayweather.model

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import se.bynumbers.daresayweather.util.PermissionStatusListener
import se.bynumbers.daresayweather.R

data class LocationData(
    val longitude: Double,
    val latitude: Double
)
class LocationLiveData(context: Context) : LiveData<LocationData>() {
    private val TAG = "LocationLiveData"
    var _context = context
    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    companion object {

        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                setLocationData(location)
            }
        }
    }
    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onActive() {
        super.onActive()
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.also {
                    setLocationData(it)
                }
            }
            .addOnFailureListener { error ->
                Log.e(TAG, "Could not get last location: ${error.message}")
            }
        startLocationUpdates()

    }


    private fun startLocationUpdates() {
        if(checkLocationPermission()) {
            Log.i(TAG, "Start location updates ")
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        } else {
            Toast.makeText(_context, R.string.no_location_check, Toast.LENGTH_SHORT).show()
        }
    }
    private fun setLocationData(location: Location) {
        Log.i(TAG, "Setting location: ${location.longitude}, ${location.latitude}")
        value = LocationData(
            longitude = location.longitude,
            latitude = location.latitude
        )
    }
    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            _context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            _context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

}
class LocationListener(application : Application) : AndroidViewModel(application) {

    private var locationData = LocationLiveData(application)

    fun getLocationData() = locationData

    val locationPermissionStatusLiveData = PermissionStatusListener(application.applicationContext,
        Manifest.permission.ACCESS_FINE_LOCATION)
}