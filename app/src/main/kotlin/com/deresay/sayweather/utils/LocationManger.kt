package com.deresay.sayweather.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.daresay.sayweather.R


object LocationManger {
    @SuppressLint("MissingPermission")
    fun location(context: Context?, fetchWeatherInfo: (location: Location) -> Unit, onError: (message: String?) -> Unit) {
        (context?.getSystemService(Context.LOCATION_SERVICE)).apply {
            when (this) {
                is LocationManager -> {
                    if (SayWeatherUtil.isConnectedToNetwork(context).not()) {
                        onError(context?.getString(R.string.network_not_enabled))
                        return
                    } else if (this.isProviderEnabled(LocationManager.NETWORK_PROVIDER).not()) {
                        onError(context?.getString(R.string.location_service_un_available))
                        return
                    }
                    //Fetch location.
                    requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, object : LocationListener {
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
