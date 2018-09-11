package com.suroid.weatherapp.ui.cityselection

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.suroid.weatherapp.R
import com.suroid.weatherapp.WeatherApplication
import com.suroid.weatherapp.models.City

/**
 * Adapter for recycler of [CitySelectionActivity] , it can handle grid and list layout
 */
class CitySelectionAdapter(private val context: Context, private val cityAdapterDelegate: CityAdapterDelegate) : RecyclerView.Adapter<CitySelectionAdapter.CityViewHolder>() {

    interface CityAdapterDelegate {
        fun onItemClick(city: City)
    }

    private lateinit var cityList: List<City>
    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CitySelectionAdapter.CityViewHolder {
        return CityViewHolder(layoutInflater.inflate(R.layout.item_city_list, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return if (::cityList.isInitialized) cityList.size else 0
    }

    override fun onBindViewHolder(viewHolder: CitySelectionAdapter.CityViewHolder, position: Int) {
        viewHolder.onBind(cityList[position])
        viewHolder.itemView.setOnClickListener {
            cityAdapterDelegate.onItemClick(cityList[position])
        }
    }

    /**
     * Updates the city List and notifies the adapter
     * @param cityList updated cities list
     */
    fun updateCityList(cityList: List<City>) {
        this.cityList = cityList
        notifyDataSetChanged()
    }


    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvName = itemView.findViewById<TextView?>(R.id.tv_name)

        fun onBind(city: City) {
            tvName?.text = tvName?.context?.getString(R.string.city_country_format, city.name, city.country)
        }

    }

}
