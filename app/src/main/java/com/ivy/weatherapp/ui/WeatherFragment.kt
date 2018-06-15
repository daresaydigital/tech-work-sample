package com.ivy.weatherapp.ui

import com.ivy.weatherapp.R
import com.ivy.weatherapp.ui.base.BaseFragment
import org.koin.android.architecture.ext.viewModel

class WeatherFragment : BaseFragment() {

    val viewModel: WeatherViewModel by viewModel<WeatherViewModel>()
    override fun getLayoutId() = R.layout.fragment_weather

}