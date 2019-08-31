package com.example.weatherapp.ui

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.example.weatherapp.database.entities.CityWeather

class CityWeatherViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var availableCityWeathers = emptyList<CityWeather>()
    override fun getItem(position: Int) = CityFragment.newInstance(availableCityWeathers[position])

    override fun getCount() = availableCityWeathers.size

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun update(cityWithWeathers: List<CityWeather>) {
        availableCityWeathers = cityWithWeathers
        notifyDataSetChanged()
    }
}