package com.baryshev.dmitriy.daresayweather.common.data.geo

import android.annotation.SuppressLint
import android.content.Context
import android.os.HandlerThread
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import io.reactivex.Single
import io.reactivex.processors.PublishProcessor


/**
 * 4/12/2018.
 */
class GeoRepo(val context: Context) {
    companion object {
        private const val MAX_WAIT_TIME = 2000L
    }

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val processor: PublishProcessor<Pair<Float, Float>> = PublishProcessor.create()
    private val locationCallback: LocationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                locationResult.locations.forEach {
                    processor.offer(Pair(it.latitude.toFloat(), it.longitude.toFloat()))
                }
            }
        }
    }

    private val locationLooper = HandlerThread("").let {
        it.start()
        it.looper
    }

    @SuppressLint("MissingPermission")
    fun getCurrentCoordinates(): Single<Pair<Float, Float>> {
        fusedLocationClient.requestLocationUpdates(LocationRequest().setMaxWaitTime(MAX_WAIT_TIME),
                                                   locationCallback,
                                                   locationLooper)
        return processor.firstOrError()
    }

    fun clearLocationUpdating() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

}