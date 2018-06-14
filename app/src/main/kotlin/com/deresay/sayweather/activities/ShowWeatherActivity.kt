package com.deresay.sayweather.activities

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.daresay.sayweather.BuildConfig
import com.daresay.sayweather.R
import com.deresay.sayweather.utils.ApiInterface
import com.deresay.sayweather.utils.LocationManger
import com.deresay.sayweather.utils.RetrofitUtils
import com.deresay.sayweather.utils.SayWeatherUtil
import kotlinx.android.synthetic.main.activity_show_weather.*
import pub.devrel.easypermissions.EasyPermissions
import java.util.*

class ShowWeatherActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    companion object {
        const val RC_LOCATION = 10001
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS).not()) {
            return
        }

        //Request Location.
        LocationManger.location(this, { location ->
            RetrofitUtils.initRetrofit(ApiInterface::class.java)
                    .fetchWeather(latitude = location.latitude,
                            longitude = location.longitude,
                            apiKey = BuildConfig.SAY_WEATHER_API_KEY).subscribe(
                            {
                                println(it.locationName)
                                //todo display weather info in ui.
                            },
                            {
                                it.printStackTrace()
                            }
                    )
        })
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        //todo snack bar no location
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_weather)
        weatherLayout.background = ContextCompat.getDrawable(this, backgroundColor())

        //Request location permission.
        EasyPermissions.requestPermissions(this,
                getString(R.string.location_permission_rationale),
                RC_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * Get the background color according to time.
     */
    private fun backgroundColor() =
            when (SayWeatherUtil.background(Date())) {
                SayWeatherUtil.BACKGROUND_COLOR.NIGHT -> R.color.colorNight
                SayWeatherUtil.BACKGROUND_COLOR.DAY -> R.color.colorDay
                SayWeatherUtil.BACKGROUND_COLOR.EVENING -> R.color.colorEvening
                else -> R.color.colorMorning
            }
}
