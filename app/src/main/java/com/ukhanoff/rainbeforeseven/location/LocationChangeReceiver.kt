package com.ukhanoff.rainbeforeseven.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager


class LocationChangeReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, p1: Intent?) {
        val locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //TODO handle location provider change and trigger weather
        }
    }

}