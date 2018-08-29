package com.example.gustens.darsey_arbetsprov_kotlin.ui.mainScreen

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domain.model.ListForecast
import com.example.gustens.darsey_arbetsprov_kotlin.R
import kotlinx.android.synthetic.main.view_holder_forecast.view.*
import java.text.SimpleDateFormat
import java.util.*

class MainAdapter() : RecyclerView.Adapter<MainViewHolder>() {

    private val LOG_TAG = MainAdapter::class.java.name

    private var mForecastList: List<ListForecast>? = null

    private lateinit var mOnItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onForecastItemClick(forecast : ListForecast)
    }

    internal fun setAdapterList(forecastList: List<ListForecast>) {
        mForecastList = forecastList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_forecast, parent, false)
        return MainViewHolder(itemView)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val forecast = mForecastList!![position]


        val dateLong: Long = forecast.dt!!*1000
        val date = Date(dateLong)

        val f = SimpleDateFormat("MMM")
        val f1 = SimpleDateFormat("dd")
        val f2 = SimpleDateFormat("HH:MM")


        val filename: String = (f1.format(date) +" "+ f.format(date)+" "+f2.format(date))

        Log.e(LOG_TAG, "Date test: "+filename)

        holder.itemView.textView_forecast.setText(filename)

        holder.setweatherIcon(forecast.weather!![0].icon!!,holder.itemView)

        holder.setWindIndicator(forecast.wind!!.deg!!.toFloat(),holder.itemView)


        holder.itemView.setOnClickListener(View.OnClickListener {
            mOnItemClickListener!!.onForecastItemClick(forecast)
        }
        )


    }

    override fun getItemCount(): Int {
        return if (mForecastList != null) {
            mForecastList!!.size
        } else 0
    }



}