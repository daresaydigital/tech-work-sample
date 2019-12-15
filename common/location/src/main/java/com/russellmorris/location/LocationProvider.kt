package com.russellmorris.location

import android.app.Activity

interface LocationProvider {
    fun getLocation(activity: Activity)
    fun initialiseLocationClient()
}