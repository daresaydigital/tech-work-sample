package com.russellmorris.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class LocationProviderImpl (private val context: Context,
                            private val locationResultListener: LocationResultListener) : LocationProvider {
    private val fusedLocationProviderClient: FusedLocationProviderClient
            = LocationServices.getFusedLocationProviderClient(context)
    private val locationCallback: LocationCallback =
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResultListener.setLocation(locationResult.lastLocation)
            }
        }
    private val locationRequest: LocationRequest = LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setInterval(LOCATION_REQUEST_INTERVAL.toLong())
        .setFastestInterval(LOCATION_REQUEST_FASTEST_INTERVAL.toLong())
    private val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val sharedPreferences: SharedPreferences  =
        context.getSharedPreferences(LOCATION_PERMISSION_PREFS, Context.MODE_PRIVATE)
    private val sharedPreferencesEditor = sharedPreferences.edit()

    override fun initialiseLocationClient() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun getLocation(activity: Activity) {
        if (!isGooglePlayServicesAvailable(activity)) {
            return
        }
        if (!isPermissionGranted) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, FINE_LOCATION) &&
                ActivityCompat.shouldShowRequestPermissionRationale(activity, COARSE_LOCATION)) {
                requestPermission(activity)
            } else {
                if (isFirstTimeAskingForPermissions(permissions)) {
                    firstLocationPermissionRequest(FINE_LOCATION)
                    firstLocationPermissionRequest(COARSE_LOCATION)
                    requestPermission(activity)
                } else {
                    locationResultListener.locationPermissionPreviouslyDeniedWithNeverAskAgain()
                }
            }
            return
        }
        if (!isLocationEnabled) {
            promptUserToEnableLocation(activity)
            return
        }
        lastKnownLocation
    }

    private val isLocationEnabled: Boolean
        get() = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    private val isPermissionGranted: Boolean
        get() = ContextCompat.checkSelfPermission(context, FINE_LOCATION) == GRANTED &&
                ContextCompat.checkSelfPermission(context, COARSE_LOCATION) == GRANTED


    private fun promptUserToEnableLocation(activiy: Activity) {
        val builder =
            LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        LocationServices
            .getSettingsClient(activiy)
            .checkLocationSettings(builder.build())
            .addOnSuccessListener { _: LocationSettingsResponse? -> lastKnownLocation }
            .addOnFailureListener { e: Exception ->
                val status = (e as ApiException).statusCode
                if (status == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                    try {
                        val resolvableApiException =
                            e as ResolvableApiException
                        resolvableApiException.startResolutionForResult(
                            activiy,
                            LOCATION_REQUEST_CODE
                        )
                    } catch (exception: SendIntentException) {
                        exception.printStackTrace()
                    }
                }
            }
    }

    private fun isFirstTimeAskingForPermissions(permissions: Array<String>): Boolean {
        return sharedPreferences.getBoolean(permissions[0], true) &&
                sharedPreferences.getBoolean(permissions[1], true)
    }

    private val lastKnownLocation: Unit
    get() { fusedLocationProviderClient.lastLocation.addOnCompleteListener { locationTask: Task<Location?> ->
                val location = locationTask.result
                if (location == null) {
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                } else {
                    locationResultListener.setLocation(location)
                }
            }
    }

    fun requestPermission(activiy: Activity) {
        ActivityCompat.requestPermissions(
            activiy,
            permissions,
            LOCATION_REQUEST_CODE
        )
    }

    private fun firstLocationPermissionRequest(permission: String) {
        sharedPreferencesEditor.putBoolean(permission, false)
        commitPrefs()
    }

    private fun commitPrefs() {
        sharedPreferencesEditor.commit()
    }

    private fun isGooglePlayServicesAvailable(activiy: Activity): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(context)
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(
                    activiy,
                    status,
                    GOOGLE_API_ERROR_CODE
                ).show()
            }
            return false
        }
        return true
    }

    companion object {
        private const val LOCATION_REQUEST_CODE = 1000
        private const val GOOGLE_API_ERROR_CODE = 1001
        private const val LOCATION_REQUEST_INTERVAL = 0
        private const val LOCATION_REQUEST_FASTEST_INTERVAL = 0

        private const val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        private const val COARSE_LOCATION =
            Manifest.permission.ACCESS_COARSE_LOCATION
        private const val GRANTED = PackageManager.PERMISSION_GRANTED
        private const val LOCATION_PERMISSION_PREFS = "location_prefs"
        private val permissions =
            arrayOf(FINE_LOCATION, COARSE_LOCATION)
    }

}