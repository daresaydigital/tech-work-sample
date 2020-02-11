package com.sneha.weatherapp.ui.weather

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sneha.weatherapp.R
import com.sneha.weatherapp.di.component.ActivityComponent
import com.sneha.weatherapp.ui.base.BaseActivity
import com.sneha.weatherapp.ui.weather.adapter.DailyForecastAdapter
import com.sneha.weatherapp.ui.weather.adapter.ForecastAdapter
import com.sneha.weatherapp.utils.common.TimeUtils
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.layout_weather_details.*
import javax.inject.Inject
import com.sneha.weatherapp.data.model.Weather
import com.sneha.weatherapp.ui.weather.adapter.ClickListener
import kotlinx.android.synthetic.main.activity_location.*
import javax.inject.Named


class WeatherActivity : BaseActivity<WeatherViewModel>() {

    @Inject
    @field:Named("DAILY_FORECAST_LAYOUT_MANAGER")
    lateinit var dailyForecastLayoutManager: LinearLayoutManager

    @Inject
    @field:Named("TODAY_FORECAST_LAYOUT_MANAGER")
    lateinit var todayForecastLayoutManager: LinearLayoutManager

    @Inject
    lateinit var forecastAdapter: ForecastAdapter

    @Inject
    lateinit var dailyForecastAdapter: DailyForecastAdapter

    private val clickListener = object : ClickListener {
        override fun onItemClicked(forecast: String) {
            val intent = Intent(this@WeatherActivity, SelectedDayForecastActivity::class.java)
            intent.putExtra("forecast_data", forecast)
            startActivity(intent)
        }
    }

    override fun provideLayoutId(): Int = R.layout.activity_weather

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupObservers() {

        //fetching weather data
        viewModel.getWeatherData().observe(this, Observer {
            it?.let { weather ->
                setWeatherData(weather)
            }
        })

        //get today's forecast
        viewModel.getForecastData().observe(this, Observer {
            it?.forecastList?.let {
                tv_today_forecast_label.visibility = View.VISIBLE
                forecastAdapter.appendData(it)
            }
        })

        //get this week's forecast
        viewModel.getDailyForecastData().observe(this, Observer {
            it?.forecastList?.let { forecast ->
                tv_week_forecast_label.visibility = View.VISIBLE
                dailyForecastAdapter.appendData(forecast)
            }
        })
    }

    override fun setupView(savedInstanceState: Bundle?) {
        rv_today_forecast.layoutManager = todayForecastLayoutManager
        rv_today_forecast.adapter = forecastAdapter

        dailyForecastAdapter.clickListener = clickListener
        rv_daily_forecast.layoutManager = dailyForecastLayoutManager
        rv_daily_forecast.adapter = dailyForecastAdapter

        swipe_refresh.setOnRefreshListener {
            viewModel.refreshData()
            viewModel.getWeatherData().observe(this, Observer {
                it?.let {
                    setWeatherData(it)
                    swipe_refresh.isRefreshing = false
                }
            })
        }
    }

    private fun setWeatherData(weather: Weather) {
        tv_city_name.visibility = View.VISIBLE
        weather_details.visibility = View.VISIBLE
        tv_today_weather_details.visibility = View.VISIBLE
        tv_city_name.text = weather.cityName
        tv_updated_at.text = getString(R.string.updated).plus(" ").plus(TimeUtils.getTimeAgo(weather.dt))
        tv_pressure.text = weather.main.pressure.toString().plus(" ").plus("hpa")
        tv_humidity.text = weather.main.humidity.toString().plus("%")
        tv_wind.text = weather.wind.speed.toString().plus(" ").plus("mps")
        tv_rain.text = weather.clouds.all.toString().plus("%")
        tv_sunrise.text = TimeUtils.getTimeInExpectedFormat(weather.sys.sunrise, "HH:mm")
        tv_sunset.text = TimeUtils.getTimeInExpectedFormat(weather.sys.sunset, "HH:mm")
        tv_temperature.text = weather.main.temperature.toInt().toString().plus("\u00B0")
        tv_weather.text = weather.weather[0].description.capitalize()
        rv_temp_min_max.text = getString(R.string.expected).plus(" ")
            .plus(weather.main.temperatureMin.toInt()).plus("℃").plus("/")
            .plus(weather.main.temperatureMax.toInt()).plus("℃")
    }
}