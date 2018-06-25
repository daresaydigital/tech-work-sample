package com.deresay.sayweather.fragments


import WeatherIconMap
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
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
import kotlinx.android.synthetic.main.toolbar.*
import pub.devrel.easypermissions.EasyPermissions
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
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
        loadWeatherInfo()

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        progress.visibility = View.GONE
        notAvailableText.text = getString(R.string.allow_permission)
        notAvailableText.visibility = View.VISIBLE
        Snackbar.make(weatherLayout, R.string.no_permission, Snackbar.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
        loadWeatherInfo()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        weatherLayout.background = ContextCompat.getDrawable(context!!, backgroundColor())
        progress.visibility = View.VISIBLE
        notAvailableText.visibility = View.GONE

        //Request location permission.
        EasyPermissions.requestPermissions(this,
                getString(R.string.location_permission_rationale),
                RC_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    /**
     * Get the timing color according to time.
     */
    private fun backgroundColor() =
            when (SayWeatherUtil.timing(Date())) {
                SayWeatherUtil.TIMING.NIGHT -> R.color.colorNight
                SayWeatherUtil.TIMING.DAY -> R.color.colorDay
                SayWeatherUtil.TIMING.EVENING -> R.color.colorEvening
                else -> R.color.colorMorning
            }

    /**
     * Load weather info in UI.
     */
    private fun loadWeatherInfo() {
        if (activity?.packageManager?.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS) == false) { //Explicit check b'cause Boolean? and Boolean.
            progress.visibility = View.GONE
            notAvailableText.text = getString(R.string.no_gps)
            notAvailableText.visibility = View.VISIBLE
            Snackbar.make(weatherLayout, R.string.no_gps, Snackbar.LENGTH_SHORT).show()
            return
        }
        //Request Location.
        notAvailableText.visibility = View.GONE
        LocationManger.location(context, { location ->
            RetrofitUtils.initRetrofit(ApiInterface::class.java)
                    .fetchWeather(latitude = location.latitude,
                            longitude = location.longitude,
                            apiKey = BuildConfig.SAY_WEATHER_API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                progress?.let { progress ->
                                    //This to avoid null pointer exception.
                                    if (progress.isShown) progress.visibility = View.GONE
                                    //Toolbar
                                    locationPointer.visibility = View.VISIBLE
                                    locationName.text = it.locationName

                                    //Weather details
                                    timingText.text = timingText(Date())
                                    weatherTemp.text = SayWeatherUtil.temperature(it.weatherParams.temperature)
                                    weatherText.text = it.weather[0].description.capitalize()
                                    weatherHumidity.text = SayWeatherUtil.humidity(it.weatherParams.humidity)
                                    weatherWind.text = SayWeatherUtil.wind(it.wind)
                                    //Show icon
                                    weatherIcon.visibility = View.VISIBLE
                                    val iconName = it.weather[0].iconName
                                    val iconIdString = WeatherIconMap.weatherIcon.let { it[iconName] ?: it["default"]}
                                    val resourceId = resources.getIdentifier(
                                            iconIdString,
                                            "drawable",
                                            context?.packageName
                                    )
                                    weatherIcon.setImageResource(resourceId)
                                }

                            },
                            {
                                Snackbar.make(weatherLayout, R.string.something_wrong, Snackbar.LENGTH_SHORT).show()
                                it.printStackTrace()
                            }
                    )
        }, { error ->
            error?.let {
                progress.let {
                    Snackbar.make(weatherLayout, error, Snackbar.LENGTH_SHORT).show()
                    notAvailableText.text = error
                    progress.visibility = View.GONE
                    notAvailableText.visibility = View.VISIBLE
                }

            }
        })
    }

    /**
     * When a time is passed the corresponding timing text is returned.
     */
    fun timingText(time: Date): String {
        return when (SayWeatherUtil.timing(time)) {
            SayWeatherUtil.TIMING.NIGHT -> getString(R.string.timing_night)
            SayWeatherUtil.TIMING.DAY -> getString(R.string.timing_day)
            SayWeatherUtil.TIMING.EVENING -> getString(R.string.timing_evening)
            else -> getString(R.string.timing_morning)
        }
    }
}

