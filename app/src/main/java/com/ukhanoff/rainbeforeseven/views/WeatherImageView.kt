package com.ukhanoff.rainbeforeseven.views

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.google.gson.JsonParser

class WeatherImageView : AppCompatImageView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun showImgIcon(iconId: Int) {
        val fileName = "icons_map.json"
        val jsonString = context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
        val res = JsonParser().parse(jsonString).asJsonObject
                .get(iconId.toString()).asJsonObject.get("icon").asString
        val resID = resources.getIdentifier(res!!,
                "drawable", context.packageName)
        this.setImageResource(resID)
    }
}
