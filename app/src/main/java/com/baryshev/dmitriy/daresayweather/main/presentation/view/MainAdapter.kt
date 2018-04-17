package com.baryshev.dmitriy.daresayweather.main.presentation.view

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.baryshev.dmitriy.daresayweather.R
import com.baryshev.dmitriy.daresayweather.main.domain.MainData

class MainAdapter : ListAdapter<MainData.Forecast, MainAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<MainData.Forecast> = object : DiffUtil.ItemCallback<MainData.Forecast>() {
            override fun areItemsTheSame(oldItem: MainData.Forecast?,
                                         newItem: MainData.Forecast?): Boolean =
                    oldItem == newItem

            override fun areContentsTheSame(oldItem: MainData.Forecast?,
                                            newItem: MainData.Forecast?): Boolean =
                    oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_forecast,
                                                               parent,
                                                               false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecast = getItem(position)
        holder.bind(forecast)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvDate: TextView? = itemView.findViewById(R.id.tvDate)
        private val tvDay: TextView? = itemView.findViewById(R.id.tvDay)
        private val tvTempMin: TextView? = itemView.findViewById(R.id.tvTempMin)
        private val tvTempMax: TextView? = itemView.findViewById(R.id.tvTempMax)
        private val ivCondition: ImageView? = itemView.findViewById(R.id.ivForecast)

        internal fun bind(forecast: MainData.Forecast) {
            with(forecast) {
                tvDate?.text = date
                tvDay?.text = day
                tvTempMin?.text = minTemp
                tvTempMax?.text = maxTemp
                ivCondition?.setImageResource(iconCondition)
            }
        }
    }
}