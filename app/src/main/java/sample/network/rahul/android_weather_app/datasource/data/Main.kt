package sample.network.rahul.android_weather_app.datasource.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Main {
    @SerializedName("temp")
    @Expose
    var temp: Double? = null
    @SerializedName("pressure")
    @Expose
    var pressure: Double? = null
    @SerializedName("humidity")
    @Expose
    var humidity: Double? = null
    @SerializedName("temp_min")
    @Expose
    var tempMin: Double? = null
    @SerializedName("temp_max")
    @Expose
    var tempMax: Double? = null
}