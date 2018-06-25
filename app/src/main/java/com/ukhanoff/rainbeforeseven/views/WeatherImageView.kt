package com.ukhanoff.rainbeforeseven.views

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.util.Log
import com.google.gson.JsonParser
import com.ukhanoff.rainbeforeseven.R

private const val ICONS_MAP_FILE = "icons_map.json"
private const val ICON = "icon"
private const val DRAWABLE_TYPE = "drawable"
private const val TAG = "WeatherImageView"

class WeatherImageView : AppCompatImageView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun showImgIcon(iconId: Int) = try {
        val jsonString = context.assets.open(ICONS_MAP_FILE).bufferedReader().use {
            it.readText()
        }
        val res = JsonParser().parse(jsonString).asJsonObject
                .get(iconId.toString()).asJsonObject.get(ICON).asString
        val resID = resources.getIdentifier(res!!,
                DRAWABLE_TYPE, context.packageName)
        this.setImageResource(resID)
    } catch (e: Exception) {
        Log.e(TAG, context.getString(R.string.image_view_issue), e)
        this.setImageResource(R.color.colorAccent)
    }
}
