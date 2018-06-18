package com.ukhanoff.rainbeforeseven.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ukhanoff.rainbeforeseven.R
import com.ukhanoff.rainbeforeseven.model.CurrentWeatherModel
import com.ukhanoff.rainbeforeseven.viewmodel.WeatherViewModel

class WeatherFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.weather_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureViewModel()

    }

    fun configureViewModel() {
        var viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        viewModel.init()

        val currentWeatherObserver = Observer<CurrentWeatherModel> { currentWeather ->
            if (currentWeather != null) {
                upateUI(currentWeather)
            }
        }

        viewModel.getCurrentWeather().observe(this, currentWeatherObserver)
    }

    fun upateUI(currentWeatherModel: CurrentWeatherModel) {
        Log.e("WEATHER", "WEATHER = " + currentWeatherModel.weather.description )

    }

}
