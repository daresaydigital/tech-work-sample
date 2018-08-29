package com.example.gustens.darsey_arbetsprov_kotlin.ui.mainScreen

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.gustens.darsey_arbetsprov_kotlin.app.Constants
import com.example.gustens.darsey_arbetsprov_kotlin.ui.windDirectionInd.WindDirIndView
import kotlinx.android.synthetic.main.view_holder_forecast.view.*

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val LOG_TAG = MainViewHolder::class.java.simpleName

    private lateinit var testText : TextView


    init {


        testText = itemView.textView_forecast

    }

    fun setweatherIcon(iconCode: String, itemView : View) {

        val imageTest : ImageView = itemView.image_weather_icon_holder

        Glide.with(itemView)
                .load(Constants.ICON_BASE_URL+iconCode+ Constants.ICON_IMAGE_FORMAT)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        Log.e(LOG_TAG, "onLoadFailed")
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        Log.e(LOG_TAG, "onResourceReady")
                        return false
                    }
                })
                .into(imageTest)
    }

    fun setWindIndicator(degree: Float, itemView : View){
        val windIndicator : WindDirIndView = itemView.wind_direction_indicator_forecast
        val startDegree : Float = 0f;
        windIndicator.setWind( startDegree, degree)
        windIndicator.setTextSize(16f)
    }

}