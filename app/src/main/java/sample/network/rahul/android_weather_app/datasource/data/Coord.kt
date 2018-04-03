package sample.network.rahul.android_weather_app.datasource.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Coord {
    @SerializedName("lon")
    @Expose
    var lon: Float? = null
    @SerializedName("lat")
    @Expose
    var lat: Float? = null
}