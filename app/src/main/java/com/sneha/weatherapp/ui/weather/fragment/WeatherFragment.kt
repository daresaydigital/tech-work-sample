package com.sneha.weatherapp.ui.weather.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.sneha.weatherapp.R
import com.sneha.weatherapp.di.component.FragmentComponent
import com.sneha.weatherapp.ui.base.BaseFragment

class WeatherFragment : BaseFragment<WeatherFragmentViewModel>() {

    companion object {

        const val TAG = "WeatherFragment"

        fun newInstance(): WeatherFragment {
            val args = Bundle()
            val fragment = WeatherFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun provideLayoutId(): Int = R.layout.fragment_weather

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupObservers() {
        viewModel.getWeatherData().observe(this, Observer {
        })
    }

    override fun setupView(view: View) {
    }
}