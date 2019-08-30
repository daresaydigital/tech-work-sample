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

        subscribeUi()
    }

    private fun subscribeUi() {
        weatherViewModel.currentWeather.observe(this, Observer {
            it?.let {

                // cityOrLocation.text = it.city.name

                currentTemp.text = "${it.weather.temp.fromKelvinToDegree()}\u00B0"

                minTemp.text = "${it.weather.minTemp.fromKelvinToDegree()}\u00B0"
                maxTemp.text = "${it.weather.maxTemp.fromKelvinToDegree()}\u00B0"

                val moodColor = when {
                    it.weather.description.contains("rains", true) -> R.color.weather_average
                    it.weather.description.contains("storm", true) -> R.color.weather_bad
                    else -> R.color.weather_good
                }

                activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), moodColor)
                //root.setBackgroundColor(ContextCompat.getColor(this@WeatherActivity, moodColor))
                root.background = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(
                        ContextCompat.getColor(requireContext(), moodColor),
                        lightenColor(
                            ContextCompat.getColor(
                                requireContext(),
                                moodColor
                            ), 0.2f
                        )
                    )
                )

                weatherStatus.text = it.weather.description

                Picasso.get().load("http://openweathermap.org/img/wn/${it.weather.icon}@2x.png").into(currentWeatherIcon);
            }

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