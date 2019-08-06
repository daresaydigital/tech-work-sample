package com.example.weatherapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.models.City
import com.example.weatherapp.viewModels.CityAddViewModel.CityEvent
import com.example.weatherapp.viewModels.CityAddViewModel.CityEvent.SelectCity
import kotlinx.android.synthetic.main.item_city.view.*

class CityAddAdapter(context: Context, private val handler: (CityEvent) -> Unit) :
    RecyclerView.Adapter<CityAddAdapter.CityViewHolder>() {
    private var filteredCities: List<City> = emptyList()

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = layoutInflater.inflate(R.layout.item_city, parent, false)
        val viewHolder = CityViewHolder(view)
        view.setOnClickListener {
            if (viewHolder.adapterPosition != -1)
                handler.invoke(SelectCity(filteredCities.get(viewHolder.adapterPosition)))
        }
        return viewHolder
    }

    fun update(cities: List<City>) {
        filteredCities = cities
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return filteredCities.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(filteredCities[position])
    }


    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: City) {
            itemView.name.text = city.name
        }
    }
}