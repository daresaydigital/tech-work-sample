package com.sneha.weatherapp.ui.weather.adapter

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.sneha.weatherapp.R
import com.sneha.weatherapp.data.model.Forecast
import com.sneha.weatherapp.di.component.ViewHolderComponent
import com.sneha.weatherapp.ui.base.BaseItemViewHolder
import com.sneha.weatherapp.utils.display.WeatherToImage
import kotlinx.android.synthetic.main.item_view_forecast.view.*

class ForecastItemViewHolder(parent: ViewGroup) :
    BaseItemViewHolder<Forecast.Item, ForecastItemViewModel>(R.layout.item_view_forecast, parent) {

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) {
        viewHolderComponent.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.time.observe(this, Observer {
            itemView.tv_time.text = it
        })

        viewModel.feelsLike.observe(this, Observer {
            itemView.tv_feels_like.text = it
        })

        viewModel.icon.observe(this, Observer {
            itemView.iv_image.setImageResource(WeatherToImage.getImageForCode(it))
        })
    }

    override fun setupView(view: View) {
        view.setOnClickListener {
            viewModel.onItemClick(adapterPosition)
        }
    }
}