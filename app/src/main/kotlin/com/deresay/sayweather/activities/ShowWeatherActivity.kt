package com.deresay.sayweather.activities

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.daresay.sayweather.R
import com.deresay.sayweather.ApiInterface
import com.deresay.sayweather.utils.LocationManger
import com.deresay.sayweather.utils.RetrofitUtils
import pub.devrel.easypermissions.EasyPermissions

class ShowWeatherActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    companion object {
        const val RC_LOCATION = 10001
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS).not()) {
            return
        }

        //Request Location.
        LocationManger.location(this,{location ->

            RetrofitUtils.initRetrofit(ApiInterface::class.java)
                    .fetchWeather(latitude = location.latitude,
                            longitude = location.longitude,
                            apiKey = "").subscribe(
                            {
                               //todo display weather info in ui.
                            },
                            {
                                it.printStackTrace()
                            }
                    )
        })
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_weather)
        EasyPermissions.requestPermissions(this,
                getString(R.string.location_permission_rationale),
                RC_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
