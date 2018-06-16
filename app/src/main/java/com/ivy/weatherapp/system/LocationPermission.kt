package com.ivy.weatherapp.system

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat


interface Permissions {
    fun hasLocationPermission(): Boolean
}

class PermissionsImpl(
        private val context: Context
) : Permissions {

    override fun hasLocationPermission() =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}