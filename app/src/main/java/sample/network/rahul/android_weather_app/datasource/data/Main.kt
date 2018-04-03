package sample.network.rahul.android_weather_app.datasource.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Main {
    @SerializedName("temp")
    @Expose
    var temp: Int? = null
    @SerializedName("pressure")
    @Expose
    var pressure: Int? = null
    @SerializedName("humidity")
    @Expose
    var humidity: Int? = null
    @SerializedName("temp_min")
    @Expose
    var tempMin: Int? = null
    @SerializedName("temp_max")
    @Expose
    var tempMax: Int? = null
}