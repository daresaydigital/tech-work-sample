package com.sneha.weatherapp.ui.weather

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.sneha.weatherapp.R
import com.sneha.weatherapp.di.component.ActivityComponent
import com.sneha.weatherapp.ui.base.BaseActivity

class WeatherActivity : BaseActivity<WeatherViewModel>() {

    companion object {
        const val TAG = "WeatherActivity"
    }

    override fun provideLayoutId(): Int = R.layout.activity_weather

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupObservers() {
        viewModel.getWeatherData().observe(this, Observer {
            Log.e(TAG, "${it?.name}")
        })
    }

    override fun setupView(savedInstanceState: Bundle?) {
    }
}