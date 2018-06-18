package com.ukhanoff.rainbeforeseven.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ukhanoff.rainbeforeseven.R
import com.ukhanoff.rainbeforeseven.extensions.getViewModel
import com.ukhanoff.rainbeforeseven.model.CurrentWeatherModel
import com.ukhanoff.rainbeforeseven.viewmodel.WeatherViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class WeatherFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { getViewModel<WeatherViewModel>(viewModelFactory) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViewModel()
    }

    private fun configureViewModel() {
        val currentWeatherObserver = Observer<CurrentWeatherModel> { currentWeather ->
            if (currentWeather != null) {
                upateUI(currentWeather)
            }
        }

        viewModel.getCurrentWeather(59.33,18.07 ).observe(this, currentWeatherObserver)
    }

    private fun upateUI(currentWeatherModel: CurrentWeatherModel) {
        Log.d("WEATHER", "WEATHER = " + currentWeatherModel.main.tempMax)
    }

}
