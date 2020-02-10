package com.sneha.weatherapp.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.sneha.weatherapp.R
import com.sneha.weatherapp.di.component.ActivityComponent
import com.sneha.weatherapp.ui.base.BaseActivity
import com.sneha.weatherapp.ui.weather.WeatherActivity
import com.sneha.weatherapp.utils.common.Event

class SplashActivity : BaseActivity<SplashViewModel>() {

    companion object {
        const val TAG = "SplashActivity"
    }

    override fun provideLayoutId(): Int = R.layout.activity_splash

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {

    }

    override fun setupObservers() {
        // Event is used by the view model to tell the activity to launch another activity
        // view model also provided the Bundle in the event that is needed for the Activity
        viewModel.launchWeather.observe(this, Observer<Event<Map<String, String>>> {
            it.getIfNotHandled()?.run {
                finish()
                startActivity(Intent(applicationContext, WeatherActivity::class.java))
            }
        })
    }
}