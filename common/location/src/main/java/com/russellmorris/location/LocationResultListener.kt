package com.russellmorris.location

import android.location.Location

interface LocationResultListener {
    fun locationPermissionPreviouslyDeniedWithNeverAskAgain()
    fun setLocation(location: Location?)
}