package com.vp.weatherapp.ui.main

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import com.vp.weatherapp.R
import com.vp.weatherapp.data.local.entity.CityWithForecast
import com.vp.weatherapp.di.Params
import com.vp.weatherapp.ui.initial.InitialActivity
import com.vp.weatherapp.ui.main.paging.BackgroundManagerImpl
import com.vp.weatherapp.ui.main.paging.PagingActivity
import com.vp.weatherapp.ui.search.SearchActivity
import com.vp.weatherapp.ui.selection.SelectionActivity
import kotlinx.android.synthetic.main.activity_paging.*
import org.koin.android.ext.android.inject


class MainActivity : PagingActivity<CityWithForecast>(), MainContract.View {


    override val presenter: MainContract.Presenter by inject { mapOf(Params.MAIN_VIEW to this) }

    override fun generatePages(entities: List<CityWithForecast>): List<Fragment> {
        var backgroundColors = listOf(Color.BLACK)
        var pages = listOf(NoCitySelectedFragment.newInstance())

        if (entities.isNotEmpty()) {
            backgroundColors = entities.map { ContextCompat.getColor(this, getWeatherColor(it.icon)) }
            pages = entities.map { WeatherFragment.newInstance(it) }
        }

        configureBackground(backgroundColors)

        return pages
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        checkDatabaseInitialized()
        super.onCreate(savedInstanceState)
        setupToolbar()
        setupStatusBar()
        setupTaskDescription()
    }

    override fun onResume() {
        super.onResume()
        presenter.getSelectedCities()
    }

    override fun onPause() {
        presenter.stop()
        super.onPause()
    }

    override fun showSelectedCities(selectedCities: List<CityWithForecast>) {
        onListReady(selectedCities)
    }


    private fun checkDatabaseInitialized() {
        val prefs = getSharedPreferences(MainActivity.PREFS_FILENAME, 0)
        val dbInitialized = prefs.getBoolean(MainActivity.DATABASE_INITIALIZED, false)
        if (!dbInitialized) {
            startActivity(InitialActivity.newIntent(this))
            finish()
        }
    }

    private fun setupToolbar() {
        btn_menu_wrapper.setOnClickListener { startActivity(SelectionActivity.newIntent(this)) }
        btn_search_wrapper.setOnClickListener { startActivity(SearchActivity.newIntent(this)) }
    }

    private fun setupStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setupTaskDescription() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val td = ActivityManager.TaskDescription(null, null, Color.TRANSPARENT)
            setTaskDescription(td)
        }
    }

    private fun getWeatherColor(icon: String?) : Int {
        return when (icon) {
            "01d" -> R.color.weather_color_clear
            "01n" -> R.color.weather_color_clear_night
            "02d" -> R.color.weather_color_cloudy
            "02n" -> R.color.weather_color_cloudy_night
            "03d" -> R.color.weather_color_cloudy
            "03n" -> R.color.weather_color_cloudy_night
            "04d" -> R.color.weather_color_cloudy
            "04n" -> R.color.weather_color_cloudy_night
            "09d" -> R.color.weather_color_stormy
            "09n" -> R.color.weather_color_stormy_night
            "10d" -> R.color.weather_color_stormy
            "10n" -> R.color.weather_color_stormy_night
            "11d" -> R.color.weather_color_stormy
            "11n" -> R.color.weather_color_stormy_night
            "13d" -> R.color.weather_color_cloudy
            "13n" -> R.color.weather_color_cloudy_night
            "50d" -> R.color.weather_color_fog
            "50n" -> R.color.weather_color_fog_night
            else -> R.color.solid_black
        }
    }

    private fun configureBackground(colors: List<Int>) {
        backgroundManager = BackgroundManagerImpl(colors)
    }


    companion object {
        const val PREFS_FILENAME = "com.vp.weather.prefs"
        const val DATABASE_INITIALIZED = "DATABASE_INITIALIZED"

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

}
