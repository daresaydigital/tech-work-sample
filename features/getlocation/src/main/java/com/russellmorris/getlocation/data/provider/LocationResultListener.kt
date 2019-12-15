package com.russellmorris.getlocation.data.provider

import android.location.Location

interface LocationResultListener {
    fun locationPermissionPreviouslyDeniedWithNeverAskAgain()
    fun setLocation(location: Location?)
}