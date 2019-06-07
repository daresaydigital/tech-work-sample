package xyz.weather.findlocweather

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.android.gms.location.*
import xyz.weather.findlocweather.constants.RequestCodeConstants
import xyz.weather.findlocweather.constants.WeatherConstants
import xyz.weather.findlocweather.modals.CityWeatherResult

class MainActivity : AppCompatActivity() {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        showWeatherDataOfLocation()
    }

    private fun showWeatherDataOfLocation() {

        findViewById<View>(R.id.textViewPermissionNotGranted).visibility = View.GONE

        if (!isLocPermitted) { //permission is not granted

            if (shouldAskForPermissionDirectly())
                ActivityCompat.requestPermissions(this@MainActivity,
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                        RequestCodeConstants.PERMISSIONS_ACCESS_LOCATION)

        } else {
            requestLocationUpdateAndSearchForWeather()
        }

    }

    private val isLocPermitted: Boolean
        get() {

            if (Build.VERSION.SDK_INT < 23) {
                return true
            }

            val permissionCheck = ContextCompat.checkSelfPermission(this@MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION)

            return (permissionCheck == PackageManager.PERMISSION_GRANTED)
        }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        //not used at the moment, would be good to start with this and continue
        // with a logic involving requestLocationUpdate

        fusedLocationClient!!.lastLocation
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful && task.result != null) {

                        val lat = task.result!!.latitude.toString()
                        val lon = task.result!!.longitude.toString()

                        OpenWeatherApi.instance.getWeather(lat, lon, object : OpenWeatherApi.WeatherResultSuccessCallback {
                            override fun onWeatherResultSuccess(result: CityWeatherResult?) {
                                if (result == null)
                                    return

                                return setViewsWithWeatherData(result)
                            }
                        })

                    }
                }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdateAndSearchForWeather() {
        //note that this app's UI is not supporting  "location services are off" case
        //some emulators may act like location services are off
        //please check with google maps app and turn on location services from the google maps app

        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY


        fusedLocationClient!!.requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {

                locationResult ?: return

                    var location = locationResult.locations[0]

                    if (location != null) {

                        val lat = location.latitude.toString()
                        val lon = location.longitude.toString()

                        //sample value
                        //val lat ="69.0015";
                        //val lon = "39.3454";

                        OpenWeatherApi.instance.getWeather(lat, lon, object : OpenWeatherApi.WeatherResultSuccessCallback {
                            override fun onWeatherResultSuccess(result: CityWeatherResult?) {
                                if (result == null)
                                    return

                                return setViewsWithWeatherData(result)
                            }
                        })

                    }

            }

        }, null)


    }

    private fun setViewsWithWeatherData(result: CityWeatherResult) {

        (findViewById<View>(R.id.textViewCity) as TextView).text = result.cityName

        (findViewById<View>(R.id.textViewDegree) as TextView).text = result.mainWeatherInfo!!.temp.toString() + "°C"

        (findViewById<View>(R.id.textViewCity) as TextView).text = result.cityName

        if (result.weather!![0].description != null) {

            val descriptionLowerCase = result.weather!![0].description!!.toLowerCase();

            (findViewById<View>(R.id.textViewWeatherEmoji) as TextView).text = WeatherConstants.getWeatherEmoji(descriptionLowerCase)
            (findViewById<View>(R.id.textViewDesc) as TextView).text = result.weather!![0].description
            (findViewById<View>(R.id.textViewBg) as TextView).text = WeatherConstants.getWeatherEmoji(descriptionLowerCase)


            (findViewById<View>(R.id.weatherLayoutContainer) ).setBackgroundColor(WeatherConstants.getWeatherBackground(descriptionLowerCase))

        }

    }

    private fun shouldAskForPermissionDirectly(): Boolean {

        //Here I could implement a logic, involving a local storage,
        //to not ask location directly everytime user launches the app
        //instead initializePermissionNotGrantedView() would be called

        return true
    }

    private fun initializePermissionNotGrantedView() {

        findViewById<View>(R.id.textViewPermissionNotGranted).visibility = View.VISIBLE
        findViewById<View>(R.id.textViewPermissionNotGranted).setOnClickListener { showWeatherDataOfLocation() }

    }

    override fun onResume() {
        super.onResume()

        if (isLocPermitted)
            showWeatherDataOfLocation()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        if (requestCode == RequestCodeConstants.PERMISSIONS_ACCESS_LOCATION) {

            if (permissions.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationUpdateAndSearchForWeather()
            } else { // Permission was denied.
                initializePermissionNotGrantedView()
            }

        }
    }
}
