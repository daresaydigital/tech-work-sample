package com.vp.weatherapp.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.vp.weatherapp.R
import com.vp.weatherapp.common.ext.inflate
import com.vp.weatherapp.data.local.entity.HourlyForecastEntity
import kotlinx.android.synthetic.main.forecast_hourly_item.view.*
import java.text.SimpleDateFormat
import java.util.*


class HourlyForecastAdapter(var items: List<HourlyForecastEntity>)
    : AbsForecastAdapter<HourlyForecastAdapter.HourlyForecastViewHolder>() {

    private val hourOfDayFormat = SimpleDateFormat("HH", Locale.getDefault())


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder =
            HourlyForecastViewHolder(parent.inflate(R.layout.forecast_hourly_item))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) =
            holder.bind(items[position])

    inner class HourlyForecastViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: HourlyForecastEntity) = with(itemView) {
            hour_of_day.text = hourOfDayFormat.format(item.dt * 1000)
            forecast_icon.setImageResource(this@HourlyForecastAdapter.getConditionDrawableByIcon(item.icon))
            hourly_temp.text = item.temp.toInt().toString()
        }
    }
}