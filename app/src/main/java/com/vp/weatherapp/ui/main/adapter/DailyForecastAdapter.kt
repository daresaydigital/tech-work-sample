package com.vp.weatherapp.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.vp.weatherapp.R
import com.vp.weatherapp.common.ext.inflate
import com.vp.weatherapp.data.local.entity.CityDailyForecast
import com.vp.weatherapp.data.local.entity.DailyForecastEntity
import kotlinx.android.synthetic.main.forecast_daily_item.view.*
import java.text.SimpleDateFormat


class DailyForecastAdapter(var items: List<DailyForecastEntity>)
    : AbsForecastAdapter<DailyForecastAdapter.DailyForecastViewHolder>() {

    private val dayOfWeekFormat = SimpleDateFormat("EEEE")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder =
            DailyForecastViewHolder(parent.inflate(R.layout.forecast_daily_item))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) =
            holder.bind(items[position])

    inner class DailyForecastViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: DailyForecastEntity) = with(itemView) {
            day_of_week.text = dayOfWeekFormat.format(item.dt * 1000)
            forecast_icon.setImageResource(this@DailyForecastAdapter.getConditionDrawableByIcon(item.icon))
            daily_max_temp.text = item.tempDay.toInt().toString()
            daily_min_temp.text = item.tempNight.toInt().toString()
        }
    }
}