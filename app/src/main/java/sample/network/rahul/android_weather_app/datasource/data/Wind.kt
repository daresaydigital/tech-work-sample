package sample.network.rahul.android_weather_app.datasource.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Wind {
    @SerializedName("speed")
    @Expose
    var speed: Float? = null
    @SerializedName("deg")
    @Expose
    var deg: Float? = null
}