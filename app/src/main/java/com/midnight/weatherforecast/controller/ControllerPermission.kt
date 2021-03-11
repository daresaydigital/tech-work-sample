package com.midnight.weatherforecast.controller

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.IntRange
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.midnight.weatherforecast.R
import java.util.*

class ControllerPermission {
    val requestCodeLocation = 101
    private val permissionLocation = Manifest.permission.ACCESS_FINE_LOCATION

    private object Holder { val INSTANCE=ControllerPermission()}
    companion object {
        val instance : ControllerPermission by lazy { ControllerPermission.Holder.INSTANCE}
    }

    /**
     *
     */
    fun getLocationPermission(context: Context,activity: Activity):Boolean{
        return if (!checkPermission(context,permissionLocation)){
            grantPermission(activity,requestCodeLocation,permissionLocation)
            false
        }else{
            true
        }
    }

    /**
     *
     */
    private fun checkPermission(context: Context,permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    /**
     *
     */
    private fun grantPermission(activity: Activity, requestCode: Int, permission: String) {
        executeGrantPermission(activity, permission, requestCode)
    }

    /**
     *
     */
    private fun executeGrantPermission(activity: Activity, permissions: String, @IntRange(from = 0L) requestCode: Int) {
        if (permissions.isNotEmpty()) {
            val perm = arrayOfNulls<String>(1)
            perm[0]=permissions
            ActivityCompat.requestPermissions(activity, perm, requestCode)
        }
    }

}