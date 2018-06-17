package com.ivy.weatherapp.ui

import android.Manifest
import android.arch.lifecycle.Observer
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import com.ivy.weatherapp.R
import com.ivy.weatherapp.data.local.model.Weather
import com.ivy.weatherapp.extention.loadUrl
import com.ivy.weatherapp.extention.toast
import com.ivy.weatherapp.ui.base.BaseFragment
import com.ivy.weatherapp.util.Failure
import kotlinx.android.synthetic.main.fragment_weather.*
import org.koin.android.architecture.ext.viewModel

class WeatherFragment : BaseFragment() {

    private val viewModel by viewModel<WeatherViewModel>()

    override fun getLayoutId() = R.layout.fragment_weather

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observePermission()
        observeWeather()
        observeError()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != 420) return
        viewModel.permission.postValue(grantResults[0] == PackageManager.PERMISSION_GRANTED)
    }

    private fun observePermission() {
        val observer = Observer<Boolean> { hasPermission ->
            if (hasPermission != true) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 420)
            }
        }
        viewModel.permission.observe(this, observer)
    }

    private fun observeWeather() {
        val observer = Observer<Weather> { weather ->
            weather ?: return@Observer
            imageIcon.loadUrl("http://openweathermap.org/img/w/${weather.icon}.png")
            textDesciption.text = resources.getString(R.string.description, weather.description.capitalize(), weather.name)
            textCurrentTemp.text = resources.getString(R.string.temp_current, weather.temp.toInt().toString())
            textTempMin.text = resources.getString(R.string.temp_low, weather.tempMin.toString())
            textTempMax.text = resources.getString(R.string.temp_high, weather.tempMax.toString())
        }
        viewModel.weather.observe(this, observer)
    }

    private fun observeError() {
        val observer = Observer<Failure> { failure ->
            failure ?: return@Observer
            when (failure) {
                is Failure.NetworkConnection -> toast(R.string.network_failure)
                is Failure.ServerError -> toast(R.string.server_failure)
                is Failure.DataError -> toast(R.string.data_failure)
            }
        }
        viewModel.error.observe(this, observer)
    }
}