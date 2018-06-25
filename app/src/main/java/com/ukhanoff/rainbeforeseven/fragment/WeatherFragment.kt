package com.ukhanoff.rainbeforeseven.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ukhanoff.rainbeforeseven.R
import com.ukhanoff.rainbeforeseven.adapters.TodayWeatherAdapter
import com.ukhanoff.rainbeforeseven.data.ForecastItem
import com.ukhanoff.rainbeforeseven.data.ForecastWeatherModel
import com.ukhanoff.rainbeforeseven.data.WeatherGlobalModel
import com.ukhanoff.rainbeforeseven.extensions.getViewModel
import com.ukhanoff.rainbeforeseven.viewmodel.WeatherViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.weather_fragment.*
import java.util.*
import javax.inject.Inject

private const val MILLISECONDS = 1000

private const val LOCATION_PROVIDERS_CHANGED = "android.location.PROVIDERS_CHANGED"
private const val NETWORK_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE"

class WeatherFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var todayWeatherAdapter: TodayWeatherAdapter
    private lateinit var updatesBroadcastReceiver: BroadcastReceiver

    private val viewModel by lazy { getViewModel<WeatherViewModel>(viewModelFactory) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        setupAndRegisterReceiver()
    }

    override fun onStop() {
        super.onStop()
        context?.unregisterReceiver(updatesBroadcastReceiver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        configureViewModel()
    }

    private fun configureViewModel() {
        val currentWeatherObserver = Observer<WeatherGlobalModel> { currentWeather ->
            if (currentWeather != null) {
                updateCurrentWeatherUI(currentWeather)
            }
        }

        val forecastObserver = Observer<ForecastWeatherModel> { forecastWeather ->
            if (forecastWeather != null) {
                updateForecastUI(forecastWeather)
            }
        }

        viewModel.getCurrentWeather().observe(this, currentWeatherObserver)
        viewModel.getForecastWeather().observe(this, forecastObserver)
        viewModel.getWeather(context!!)
    }

    private fun updateCurrentWeatherUI(currentWeatherModel: WeatherGlobalModel) {
        currentTemp.text = resources.getString(R.string.degreeFormat)
                .format(Math.round(currentWeatherModel.main.temp))
        cityName.text = currentWeatherModel.name
        mainWeatherDescr.text = currentWeatherModel.weather.first().description.capitalize()
        currentWeatherIcon.showImgIcon(currentWeatherModel.weather.first().id)

    }

    fun triggerWeatherUpdate() {
        viewModel?.getWeather(context!!)
    }

    private fun updateForecastUI(forecastWeatherModel: ForecastWeatherModel) {
        hideProgressBar()
        todayWeatherAdapter.setForecast(generateForecastList(forecastWeatherModel))
        todayWeatherAdapter.notifyDataSetChanged()
    }

    private fun setupAndRegisterReceiver() {
        updatesBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (isOnline(context!!)) {
                    when (intent?.action) {
                        LOCATION_PROVIDERS_CHANGED -> {
                            triggerWeatherUpdate()
                        }
                        NETWORK_CONNECTIVITY_CHANGE -> {
                            triggerWeatherUpdate()
                        }
                    }
                }
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(LOCATION_PROVIDERS_CHANGED)
        intentFilter.addAction(NETWORK_CONNECTIVITY_CHANGE)

        context?.registerReceiver(updatesBroadcastReceiver, intentFilter)
    }

    private fun setupAdapter() {

        todayWeatherAdapter = TodayWeatherAdapter(context!!)
        hourlyForecast?.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
            adapter = todayWeatherAdapter
            setHasFixedSize(true)
        }
    }

    private fun hideProgressBar() {
        progressBar?.visibility = View.GONE
        divider?.visibility = View.VISIBLE
    }

    private fun generateForecastList(forecastWeatherModel: ForecastWeatherModel): MutableList<ForecastItem> {
        val forecast: MutableList<ForecastItem> = arrayListOf()
        for (item in forecastWeatherModel.list) {
            item.main.temp
            forecast.add(ForecastItem(Date(item.dt * MILLISECONDS), Math.round(item.main.temp).toInt(), item.weather.first().id))
        }
        return forecast
    }

    private fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }


}
