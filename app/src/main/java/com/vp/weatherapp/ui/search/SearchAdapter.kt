package com.vp.weatherapp.ui.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.vp.weatherapp.R
import com.vp.weatherapp.common.ext.inflate
import com.vp.weatherapp.data.local.entity.CityEntity
import kotlinx.android.synthetic.main.list_item_city.view.*


class SearchAdapter(val cities: List<CityEntity>,
                    val formatCityCountry: String,
                    val listener: (CityEntity) -> Unit)
    : RecyclerView.Adapter<SearchAdapter.SearchItemViewHolder>() {


    override fun getItemCount(): Int = cities.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            SearchItemViewHolder(parent.inflate(R.layout.list_item_city))

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) =
            holder.bind(cities[position], listener)

    inner class SearchItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: CityEntity, listener: (CityEntity) -> Unit) = with(itemView) {
            city_name.text = formatCityCountry.format(item.name, item.country)
            setOnClickListener { listener(item) }
        }
    }

}