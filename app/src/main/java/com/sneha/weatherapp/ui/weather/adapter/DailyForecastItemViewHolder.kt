package com.sneha.weatherapp.ui.weather.adapter

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.sneha.weatherapp.R
import com.sneha.weatherapp.di.component.ViewHolderComponent
import com.sneha.weatherapp.ui.base.BaseItemViewHolder
import kotlinx.android.synthetic.main.item_view_daily_forecast.view.*
import kotlinx.android.synthetic.main.item_view_forecast.view.iv_image
import java.util.*
import com.google.gson.Gson
import com.sneha.weatherapp.data.model.Forecast


class DailyForecastItemViewHolder(parent: ViewGroup, var clickListener: ClickListener?) :
    BaseItemViewHolder<Forecast, DailyForecastItemViewModel>(
        R.layout.item_view_daily_forecast, parent
    ) {

    var forecast: Forecast? = null

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) {
        viewHolderComponent.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.time.observe(this, Observer {
           itemView.tv_day.text = when (position) {
                0 -> itemView.context.getString(R.string.today)
               1 -> itemView.context.getString(R.string.tomorrow)
                else -> it
            }
        })

        viewModel.feelsLike.observe(this, Observer {
            itemView.tv_feels_like.text = it
        })

        viewModel.icon.observe(this, Observer {
            itemView.iv_image.setImageResource(it)
        })

        viewModel.temp.observe(this, Observer {
            itemView.tv_temp.text = it
        })

        viewModel.forecastItem.observe(this, Observer {
            forecast = it
        })
    }

    override fun setupView(view: View) {
        view.setOnClickListener {
            clickListener?.onItemClicked(Gson().toJson(forecast))
        }
        val androidColors = this.itemView.resources.getIntArray(R.array.cardColors)
        val randomAndroidColor = androidColors[Random().nextInt(androidColors.size)]
        itemView.card_view.setBackgroundColor(randomAndroidColor)
    }
}