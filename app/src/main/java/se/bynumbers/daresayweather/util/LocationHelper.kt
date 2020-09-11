package se.bynumbers.daresayweather.util

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import se.bynumbers.daresayweather.util.Constants.Companion.SP_DARESAY_WEATHER
import se.bynumbers.daresayweather.util.Constants.Companion.SP_LOCATION_ENABLED
import se.bynumbers.daresayweather.R

class LocationHelper {

    companion object {
        private val TAG = "LocationHelper"
        const val PERMISSIONS_LOCATION = 1001
        fun checkLocationPermission(context : Context): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                    /*&& ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED*/
        }
        fun arePermissionsResultYes(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray,
            context: Context
        ) : Boolean {
            val sp = context.getSharedPreferences(SP_DARESAY_WEATHER,
                Context.MODE_PRIVATE
            )
            when (requestCode) {
                PERMISSIONS_LOCATION -> {
                    if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    ) {

                        // permission was granted, yay! Do the
                        // location-related task you need to do.
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                            == PackageManager.PERMISSION_GRANTED
                        ) {

                            val editor = sp.edit()
                            editor.putBoolean(SP_LOCATION_ENABLED, true)
                            editor.apply()

                            return true
                        }
                    }

                }
            }
            val editor = sp.edit()
            editor.putBoolean(SP_LOCATION_ENABLED, false)
            editor.apply()

            return false

        }




    }

}