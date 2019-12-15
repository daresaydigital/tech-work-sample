package com.russellmorris.getlocation.data.provider

import android.app.Activity

interface LocationProvider {
    fun getLocation(activity: Activity)
    fun initialiseLocationClient()
}