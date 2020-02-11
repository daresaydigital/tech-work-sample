package com.sneha.weatherapp.ui.weather.adapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.sneha.weatherapp.data.model.ForecastItem
import com.sneha.weatherapp.ui.base.BaseAdapter

class ForecastAdapter(
    parentLifecycle: Lifecycle,
    private val forecasts: ArrayList<ForecastItem>
) : BaseAdapter<ForecastItem, ForecastItemViewHolder>(parentLifecycle, forecasts) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ForecastItemViewHolder(parent)

    override fun getItemCount(): Int = if(forecasts.size > 10) 10 else forecasts.size
}