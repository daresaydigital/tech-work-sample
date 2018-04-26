package com.vp.weatherapp.ui.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.vp.weatherapp.R
import com.vp.weatherapp.data.local.entity.CityWithForecast
import com.vp.weatherapp.data.local.entity.DailyForecastEntity
import com.vp.weatherapp.data.local.entity.HourlyForecastEntity
import com.vp.weatherapp.di.Params.WEATHER_VIEW
import com.vp.weatherapp.ui.main.adapter.DailyForecastAdapter
import com.vp.weatherapp.ui.main.adapter.HourlyForecastAdapter
import kotlinx.android.synthetic.main.forecast_header.*
import kotlinx.android.synthetic.main.fragment_weather.*
import org.koin.android.ext.android.inject


class WeatherFragment : Fragment(), WeatherContract.View {

    private lateinit var hourlyForecastAdapter: HourlyForecastAdapter
    private lateinit var dailyForecastAdapter: DailyForecastAdapter
    private val formatDegrees by lazy { context?.getString(R.string.fmt_degrees) }

    override val presenter: WeatherContract.Presenter by inject { mapOf(WEATHER_VIEW to this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: find out what is causing "forecast_header" to have mTop increased sometimes
        // probably horizontal recycler

        setupRecyclers()
    }

    private fun setupRecyclers() {
        recycler_hourly.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
                //
            }
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                //
            }

            override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
                when (e?.action) {
                    MotionEvent.ACTION_MOVE -> rv?.parent?.requestDisallowInterceptTouchEvent(true)
                }
                return false
            }
        })

        recycler_hourly.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        hourlyForecastAdapter = HourlyForecastAdapter(emptyList())
        recycler_hourly.adapter = hourlyForecastAdapter

        recycler_daily.layoutManager = LinearLayoutManager(context)
        dailyForecastAdapter = DailyForecastAdapter(emptyList())
        recycler_daily.adapter = dailyForecastAdapter
    }

    override fun onResume() {
        super.onResume()

        val cityName = arguments!!.getString(CITY_NAME)
        city_name.text = cityName
        val cityId = arguments!!.getLong(CITY_ID)

        presenter.getCurrentWeather(cityId)
        presenter.getHourlyForecast(cityId)
        presenter.updateHourlyForecast(cityId)
        presenter.getDailyForecast(cityId)
        presenter.updateDailyForecast(cityId)
    }

    override fun onDestroy() {
        presenter.stop()
        super.onDestroy()
    }

    override fun displayCurrentWeather(forecast: HourlyForecastEntity) {
        val temp = forecast.temp.toInt().toString()
        temperature.text = formatDegrees?.format(temp)
        description.text = forecast.description
    }

    override fun displayHourlyForecast(hourlyForecasts: List<HourlyForecastEntity>) {
        hourlyForecastAdapter.items = hourlyForecasts
        hourlyForecastAdapter.notifyDataSetChanged()
        hourlyForecastAdapter.notifyItemRangeChanged(0, hourlyForecasts.size)
    }

    override fun showNoHourlyForecast() {
    }

    override fun displayDailyForecast(dailyForecasts: List<DailyForecastEntity>) {
        dailyForecastAdapter.items = dailyForecasts
        dailyForecastAdapter.notifyDataSetChanged()
        dailyForecastAdapter.notifyItemRangeChanged(0, dailyForecasts.size)
    }

    override fun showNoDailyForecast() {
    }

    override fun onApiError(message: String) {
        Snackbar.make(rootView, "ApiError: $message", Snackbar.LENGTH_LONG)
    }

    override fun onError(title: String, message: String) {
        Snackbar.make(rootView, "Error: $message", Snackbar.LENGTH_LONG)
    }

    companion object {

        const val CITY_ID = "CITY_ID"
        const val CITY_NAME = "CITY_NAME"

        fun newInstance(city: CityWithForecast): WeatherFragment {
            val args = Bundle()
            args.putLong(CITY_ID, city.cityEntity.cityId)
            args.putString(CITY_NAME, city.cityEntity.name)
            val fragment = WeatherFragment()
            fragment.arguments = args
            return fragment
        }
    }
}