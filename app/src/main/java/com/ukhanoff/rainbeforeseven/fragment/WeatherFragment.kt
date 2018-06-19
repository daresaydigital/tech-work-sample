package com.ukhanoff.rainbeforeseven.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.ukhanoff.rainbeforeseven.R
import com.ukhanoff.rainbeforeseven.adapters.TodayWeatherAdapter
import com.ukhanoff.rainbeforeseven.data.ForecastItem
import com.ukhanoff.rainbeforeseven.data.ForecastWeatherModel
import com.ukhanoff.rainbeforeseven.data.WeatherGlobalModel
import com.ukhanoff.rainbeforeseven.extensions.getViewModel
import com.ukhanoff.rainbeforeseven.viewmodel.WeatherViewModel
import com.ukhanoff.rainbeforeseven.views.WeatherImageView
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject

private const val MILLISECONDS = 1000

class WeatherFragment : DaggerFragment() {
    private var currentTemp: TextView? = null
    private var cityName: TextView? = null
    private var currentDate: TextView? = null
    private var mainWeatherDescr: TextView? = null
    private var currentWeatherIcon: WeatherImageView? = null
    private var todayWeatherList: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var divider: View? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var todayWeatherAdapter: TodayWeatherAdapter

    private val viewModel by lazy { getViewModel<WeatherViewModel>(viewModelFactory) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.weather_fragment, container, false)
        setupViews(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        currentTemp!!.text = resources.getString(R.string.degreeFormat)
                .format(Math.round(currentWeatherModel.main.temp))
        cityName!!.text = currentWeatherModel.name
        mainWeatherDescr!!.text = currentWeatherModel.weather.first().description!!.capitalize()
        currentWeatherIcon!!.showImgIcon(currentWeatherModel.weather.first().id)

    }

    private fun updateForecastUI(forecastWeatherModel: ForecastWeatherModel) {
        hideProgressBar()
        todayWeatherAdapter.setForecast(generateForecastList(forecastWeatherModel))
        todayWeatherAdapter.notifyDataSetChanged()
    }

    private fun setupViews(view: View) {
        currentTemp = view.bind(R.id.current_temp)
        cityName = view.bind(R.id.city_name)
        currentDate = view.bind(R.id.current_date)
        mainWeatherDescr = view.bind(R.id.main_weather_descr)
        currentWeatherIcon = view.bind(R.id.current_weather_icon)
        todayWeatherList = view.bind(R.id.hourly_forecast)
        progressBar = view.bind(R.id.progress_bar)
        divider = view.bind(R.id.divider)

        todayWeatherAdapter = TodayWeatherAdapter(context!!)
        todayWeatherList!!.layoutManager = (LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false))
        todayWeatherList!!.itemAnimator = DefaultItemAnimator()
        todayWeatherList!!.adapter = todayWeatherAdapter
        todayWeatherList!!.setHasFixedSize(true)
    }

    private fun hideProgressBar() {
        progressBar!!.visibility = View.GONE
        divider!!.visibility = View.VISIBLE
    }

    private fun generateForecastList(forecastWeatherModel: ForecastWeatherModel): MutableList<ForecastItem> {
        val forecast: MutableList<ForecastItem> = arrayListOf()
        for (item in forecastWeatherModel.list) {
            item.main.temp
            forecast.add(ForecastItem(Date(item.dt * MILLISECONDS), Math.round(item.main.temp).toInt(), item.weather.first().id))
        }
        return forecast
    }

    fun <T : View> View.bind(@IdRes res: Int): T {
        @Suppress("UNCHECKED_CAST")
        return findViewById(res) as T
    }

}
