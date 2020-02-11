package com.sneha.weatherapp.ui.weather.adapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.sneha.weatherapp.data.model.Forecast
import com.sneha.weatherapp.ui.base.BaseAdapter

interface ClickListener{
    fun onItemClicked(forecast: String)
}

class DailyForecastAdapter(
    parentLifecycle: Lifecycle,
    forecasts: ArrayList<Forecast>,
    var clickListener: ClickListener? = null
) : BaseAdapter<Forecast, DailyForecastItemViewHolder>(parentLifecycle, forecasts) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DailyForecastItemViewHolder(parent, clickListener)
}