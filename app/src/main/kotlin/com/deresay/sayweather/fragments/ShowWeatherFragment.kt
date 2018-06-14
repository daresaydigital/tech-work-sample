package com.deresay.sayweather.fragments


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daresay.sayweather.BuildConfig
import com.daresay.sayweather.R
import com.deresay.sayweather.utils.ApiInterface
import com.deresay.sayweather.utils.LocationManger
import com.deresay.sayweather.utils.RetrofitUtils
import com.deresay.sayweather.utils.SayWeatherUtil
import kotlinx.android.synthetic.main.fragment_show_weather.*
import pub.devrel.easypermissions.EasyPermissions
import java.util.*


/**
 * A simple [Fragment] subclass.
 *
 */
class ShowWeatherFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    companion object {
        fun newInstance() = ShowWeatherFragment()
        const val RC_LOCATION = 10001
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (activity?.packageManager?.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS) == false) { //Explicit check b'cause Boolean? and Boolean.
            return
        }

        //Request Location.
        LocationManger.location(context, { location ->
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        weatherLayout.background = ContextCompat.getDrawable(context!!, backgroundColor())

        //Request location permission.
        EasyPermissions.requestPermissions(this,
                getString(R.string.location_permission_rationale),
                RC_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
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

