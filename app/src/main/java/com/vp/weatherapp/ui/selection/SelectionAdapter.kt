package com.vp.weatherapp.ui.selection

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.View
import com.vp.weatherapp.R
import com.vp.weatherapp.common.ext.inflate
import com.vp.weatherapp.data.local.entity.CityWithForecast
import kotlinx.android.synthetic.main.selection_list_item.view.*


class SelectionAdapter(private val context: Context,
                       var items: MutableList<CityWithForecast>) :
        RecyclerView.Adapter<SelectionAdapter.ViewHolder>() {

    companion object {
        var fmtDeg: String? = null
    }

    init {
        fmtDeg = context.getString(R.string.fmt_degrees)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.selection_list_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])


    fun addItem(city: CityWithForecast) {
        items.add(city)
        notifyItemInserted(items.size)
    }

    fun removeAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItemAtPosition(position: Int) = items[position]

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(city: CityWithForecast) = with(itemView) {
            city_name.text = city.cityEntity.name
            temp.text = fmtDeg?.format(city.temp?.toInt().toString())
        }
    }
}