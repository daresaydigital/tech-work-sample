package sample.network.rahul.android_weather_app.datasource.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Clouds {

    @SerializedName("all")
    @Expose
    var all: Int? = null
}