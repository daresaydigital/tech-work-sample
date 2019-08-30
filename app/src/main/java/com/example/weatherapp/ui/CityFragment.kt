package com.example.weatherapp.ui

import android.app.Activity
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.weatherapp.R
import com.example.weatherapp.database.entities.City
import com.example.weatherapp.database.entities.CityWeather
import com.example.weatherapp.utils.lightenColor
import com.example.weatherapp.viewModels.CityViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.dataAvailable
import kotlinx.android.synthetic.main.activity_home.root
import kotlinx.android.synthetic.main.fragment_weather.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CityFragment : Fragment() {

    val weatherViewModel: CityViewModel by viewModel {
        val cityWeather = arguments?.getParcelable<CityWeather>(CITY_WEATHER_ARGUMENT)
        parametersOf(cityWeather)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachHandlers()
        subscribeUi()
    }

    private fun attachHandlers() {
        swipeToRefreshCity.setOnRefreshListener {
            val cityWeather = arguments?.getParcelable<CityWeather>(CITY_WEATHER_ARGUMENT)
            val cityId = cityWeather?.id
            if (cityId != null)
                weatherViewModel.fetchUsingCityId(cityId)
            else swipeToRefreshCity.isRefreshing = false
        }
    }

    private fun subscribeUi() {
        weatherViewModel.diffMinutes.observe(this, Observer {
            it?.let {minutesDiff ->
                when {
                    minutesDiff < 5 -> lastRefreshDiff.text = "Updated in the last 5 minutes"
                    minutesDiff < 60 -> lastRefreshDiff.text = "Updated in the last hour"
                    minutesDiff < 120 -> lastRefreshDiff.text = "Updated in the last 2 hours"
                    minutesDiff < 4 * 60 -> lastRefreshDiff.text = "Updated in the last ${minutesDiff/60} hours"
                    else -> lastRefreshDiff.text = "expired weather info"
                }
            }

        })

        weatherViewModel.currentWeather.observe(this, Observer {
            it?.let {

                currentTemp.text = "${it.weather.temp.fromKelvinToDegree()}\u00B0"

                minTemp.text = "${it.weather.minTemp.fromKelvinToDegree()}\u00B0"
                maxTemp.text = "${it.weather.maxTemp.fromKelvinToDegree()}\u00B0"

                weatherStatus.text = it.weather.description

                Picasso.get().load("http://openweathermap.org/img/wn/${it.weather.icon}@2x.png")
                    .into(currentWeatherIcon);


            }

            swipeToRefreshCity.isRefreshing = false
        })
    }


    companion object {
        private val CITY_WEATHER_ARGUMENT = "city_weather"
        fun newInstance(cityWeather: CityWeather) = CityFragment().apply {
            arguments = Bundle().apply {
                putParcelable(CITY_WEATHER_ARGUMENT, cityWeather)
            }
        }
    }
}