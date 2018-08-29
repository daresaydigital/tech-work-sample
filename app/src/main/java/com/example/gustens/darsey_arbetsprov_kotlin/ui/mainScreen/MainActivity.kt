package com.example.gustens.darsey_arbetsprov_kotlin.ui.mainScreen

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Address
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.gustens.darsey_arbetsprov_kotlin.R
import com.example.gustens.darsey_arbetsprov_kotlin.app.Constants
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.domain.model.Clouds
import com.example.domain.model.ListForecast
import com.example.domain.model.Main
import com.example.domain.model.Weather
import com.example.gustens.darsey_arbetsprov_kotlin.ui.windDirectionInd.WindDirIndView


class MainActivity : AppCompatActivity(), MainContract.View,MainAdapter.OnItemClickListener {

    private val LOG_TAG = MainActivity::class.java.name

    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0

    private lateinit var mMainViewModel: MainContract.ViewModel

    private lateinit var mainRecyclerView: RecyclerView

    private lateinit var mMainAdapter: MainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMainAdapter = MainAdapter()
        mMainAdapter.setOnItemClickListener(this)
        mainRecyclerView = recyclerView_forecast;
        mainRecyclerView!!.adapter = mMainAdapter

        mainRecyclerView!!.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        (mMainViewModel as MainViewModel).init(this, LocationServices.getFusedLocationProviderClient(this))


    }

    override fun onResume() {
        super.onResume()
        mMainViewModel.onResume()
    }


    override fun onPause() {
        super.onPause()
        mMainViewModel.onPause()
    }

    override fun shouldShowPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

        } else {

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                   mMainViewModel.startLocationUpdates()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

        // Add other 'when' lines to check for other
        // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }


    override fun setweatherIcon(iconCode: String) {

        val loadingInd : ProgressBar = progresBar_loading_icon
        loadingInd.visibility = View.GONE

        val imageTest : ImageView = image_weather_icon

        Glide.with(this)
                .load(Constants.ICON_BASE_URL+iconCode+Constants.ICON_IMAGE_FORMAT)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        Log.e(LOG_TAG, "onLoadFailed")
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        Log.e(LOG_TAG, "onResourceReady")
                        return false
                    }
                })
                .into(imageTest)
    }

    override fun setWind(speed: String, degreeOld : Float, degreeNew : Float) {

        Log.e(LOG_TAG, "setWind")
        val windIndicator : WindDirIndView = wind_direction_indicator
        windIndicator.setWind( degreeOld, degreeNew)

        val windSpeed : TextView = textview_wind_speed
        val windSpeedStr : String = speed+"m/s"
        windSpeed.setText(windSpeedStr)

    }

    override fun setCloudsData(clouds: Clouds) {

    }

    override fun setMainData(main: Main) {

        val temp : TextView = textview_temperature
        val tempStr : String = main.temp+"C"
        temp.setText(tempStr)

        val humidity : TextView = textview_humidity_value
        val humidityValue : String = main.humidity+"%"
        humidity.setText(humidityValue)

        val pressure : TextView = textview_pressure_value
        val pressureValue : String = main.pressure+"mbar"
        pressure.setText(pressureValue)
    }

    override fun setWeather(weather: Weather) {

        val weatherDescription : TextView = textview_weather_description
        weatherDescription.setText(weather.description)

    }

    override fun setAdress(address: Address) {

        val location : TextView = textView_location
        location.setText(address.subLocality)
    }

    override fun setDewPoint(dewPoint: String) {
        val dewPointTextView : TextView = textview_dew_point_value
        dewPointTextView.setText(dewPoint)
    }

    override fun setForecastAdapter(forecastList: List<ListForecast>) {

        val progress : ProgressBar = progresBar_loading_icon_forecast
        progress.visibility = View.GONE

        mainRecyclerView.visibility = View.VISIBLE
        mMainAdapter.setAdapterList(forecastList)

    }

    override fun onForecastItemClick(forecast : ListForecast) {
        Toast.makeText(this, "Temperature: "+forecast.main!!.temp,Toast.LENGTH_SHORT).show()
    }
}
