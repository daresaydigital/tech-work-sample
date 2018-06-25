package com.ukhanoff.rainbeforeseven.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ukhanoff.rainbeforeseven.R
import com.ukhanoff.rainbeforeseven.data.ForecastItem
import kotlinx.android.synthetic.main.weather_forecast_item.view.*
import java.text.SimpleDateFormat
import java.util.*


internal class TodayWeatherAdapter(private val context: Context) : RecyclerView.Adapter<TodayWeatherAdapter.ViewHolder>() {
    private var forecast: List<ForecastItem>? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(forecastItem: ForecastItem) {
            val sdf = SimpleDateFormat("EEE, h aa ", Locale.US)
            itemView.weatherTime.text = sdf.format(forecastItem.date)
            itemView.weatherIcon.showImgIcon(forecastItem.iconId)
            itemView.weatherTemperature.text = itemView.context.resources.getString(R.string.degreeFormat)
                    .format(forecastItem.temp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.weather_forecast_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecastItem = forecast!![position]
        holder.bind(forecastItem)
    }

    override fun getItemCount(): Int {
        return if (forecast == null) 0 else forecast!!.size
    }

    fun setForecast(forecast: List<ForecastItem>) {
        this.forecast = forecast
    }

}
