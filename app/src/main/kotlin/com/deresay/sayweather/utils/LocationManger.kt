package com.deresay.sayweather.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle


object LocationManger{
    @SuppressLint("MissingPermission")
    fun location(context: Context?, fetchWeatherInfo:(location:Location) -> Unit){
       (context?.getSystemService(Context.LOCATION_SERVICE)).apply{
           when(this){
               is LocationManager -> {
                   requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0f,object :LocationListener{
                       override fun onLocationChanged(location: Location?) {
                           location?.let(fetchWeatherInfo)
                       }

                       override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                       }

                       override fun onProviderEnabled(p0: String?) {
                       }

                       override fun onProviderDisabled(p0: String?) {
                       }

                   })
               }
           }
       }
    }
}
