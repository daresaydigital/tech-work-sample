package com.artamonov.weatherapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import com.artamonov.weatherapp.WeatherParser.parseJsonWeather
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()
    private var mGoogleApiClient: GoogleApiClient? = null
    private var currentCity: String? = null
    private var responseJSON: String? = null
    private var isCityFromInput = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            showError("Please, grant Location permission to use the app!")
            return
        }

        if (savedInstanceState == null) {
            val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            mFusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                    currentCity = getCurrentCity(latitude, longitude)
                    getWeather(currentCity)
                }
            }

        } else {
            populateViews()
        }

        /**
         * The method for disabling EditText from input after rotating
         */
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        iv_enter.setOnClickListener {
            onClick()
        }
    }

    private fun showError(message: CharSequence) =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                Snackbar.make(root_layout, message,
                        Snackbar.LENGTH_LONG).show()
            } else {
                toast(message)
            }

    private fun Context.toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    private fun getWeather(city: String?) {
        val url = "http://worksample-api.herokuapp.com/weather?q=" + currentCity +
                "&key=" + API_KEY

        if (NetworkUtils.isNetworkAvailable(applicationContext)) {
            val okHttpClient = OkHttpClient()
            val request = Request.Builder()
                    .url(url)
                    .build()

            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        showError("Check your Internet connection or try later")
                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        responseJSON = response.body()!!.string()

                        runOnUiThread {
                            parseJsonWeather(responseJSON, city)
                            populateViews()
                        }
                    } else {
                        runOnUiThread {
                            showError("Response is not successful")
                        }
                    }
                }
            })
        } else {
            showError("Check your Internet connection!")
        }
    }

    private fun populateViews() {

        val summary = WeatherParser.weather.weatherSummary
        val city = WeatherParser.weather.city
        val temperature = String.format(resources
                .getString(R.string.temperature), WeatherParser.weather.temperature.toString(),
                0x00B0.toChar())
        tv_current_city.text = city
        if (city == "Unknown") {
            tv_temperature.text = null
            if (isCityFromInput) {
                showError("Please, enter valid city name!")
            }
        } else {
            tv_temperature.text = temperature
        }

        /**
         * isCityFromInput handles an exception when a user received a weather by clicking on button or
         * automatically while starting the app
         */
        isCityFromInput = false

        tv_summary.text = summary
        summary?.let { setIconType(it) }


    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(CURRENT_SUMMARY, WeatherParser.weather.weatherSummary)
        outState?.putString(CURRENT_CITY, WeatherParser.weather.city)
        WeatherParser.weather.temperature?.let { outState?.putInt(CURRENT_TEMPERATURE, it) }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        WeatherParser.weather.city = savedInstanceState?.getString(CURRENT_CITY)
        WeatherParser.weather.weatherSummary = savedInstanceState?.getString(CURRENT_SUMMARY)
        WeatherParser.weather.temperature = savedInstanceState?.getInt(CURRENT_TEMPERATURE)
    }

    private fun setIconType(summary: String) {

        when (summary) {
            "Thunderstorm" -> iv_logo!!.setImageResource(R.drawable.ic_wi_thunderstorm)
            "Drizzle" -> iv_logo!!.setImageResource(R.drawable.ic_wi_raindrop)
            "Rain" -> iv_logo!!.setImageResource(R.drawable.ic_wi_rain)
            "Snow" -> iv_logo!!.setImageResource(R.drawable.ic_wi_snow)
            "Atmosphere" -> iv_logo!!.setImageResource(R.drawable.ic_wi_windy)
            "Clear" -> iv_logo!!.setImageResource(R.drawable.ic_wi_day_sunny)
            "Clouds" -> iv_logo!!.setImageResource(R.drawable.ic_wi_cloud)
        }
    }

    private fun getCurrentCity(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)

            return addresses[0].locality
        } catch (e: IOException) {
            showError("IO Exception")
        }
        return null
    }

    override fun onConnected(bundle: Bundle?) {

    }

    override fun onConnectionSuspended(i: Int) {
        mGoogleApiClient!!.connect()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        showError("Connection failed")
    }

    fun onClick() {
        currentCity = et_enter_city!!.text.toString()
        isCityFromInput = true
        getWeather(currentCity)
    }

    companion object {
        const val API_KEY = "YOUR API"
        private const val CURRENT_CITY = "city"
        private const val CURRENT_TEMPERATURE = "temperature"
        private const val CURRENT_SUMMARY = "summary"

    }
}
