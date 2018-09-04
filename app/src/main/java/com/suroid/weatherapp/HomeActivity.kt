package com.suroid.weatherapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import com.suroid.weatherapp.ui.cityselection.CitySelectionActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fab.setOnClickListener {
            val intent = Intent(this@HomeActivity, CitySelectionActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@HomeActivity, fab, ViewCompat.getTransitionName(fab) ?: "")
            ActivityCompat.startActivity(this@HomeActivity, intent, options.toBundle())
        }

//        WeatherApplication.coreComponent.cityWeatherRepo().getAllCityWeathers().subscribeOn(Schedulers.io()).subscribe {
//            Log.d("abcd", "size is ${it.size}")
//        }

    }
}
