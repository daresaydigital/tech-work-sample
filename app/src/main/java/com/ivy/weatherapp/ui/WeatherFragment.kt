package com.ivy.weatherapp.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.ivy.weatherapp.R
import com.ivy.weatherapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_weather.*
import org.koin.android.architecture.ext.viewModel

class WeatherFragment : BaseFragment() {

    val viewModel by viewModel<WeatherViewModel>()

    override fun getLayoutId() = R.layout.fragment_weather

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weather.observe(this, Observer { weather -> println("Changed... ${weather?.temp}") })

        buttonMockData.setOnClickListener { viewModel.mockData() }
    }

}