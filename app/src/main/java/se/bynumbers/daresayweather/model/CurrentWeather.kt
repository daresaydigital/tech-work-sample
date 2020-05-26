package se.bynumbers.daresayweather.model

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.serialization.json.Json
import se.bynumbers.daresayweather.util.WeatherDataHelper

import java.lang.reflect.Array


data class Coord (
    var lon : Double = 0.0,
    var lat : Double = 0.0
)

class Weather (
    var id : Int = 0,
    var main: String? = null,
    var description: String? = null,
    var icon: String? = null
)


class Wind (
    var speed : Double = 0.0,
    var deg : Int = 0
)
data class Clouds (
    var all : Int = 0
)

data class Sys (
    var type : Int = 0,
    var id : Int = 0,
    var message : Double = 0.0,
    var country: String? = null,
    var sunrise  : Int = 0,
    var sunset : Int = 0,
    var pod: String? = null
)

data class CurrentWeather (
    var coord: Coord? = null,
    var weather: String? = null,
    var base: String? = null,
    var main: Main? = null,
    var visibility : Int = 0,
    var wind: Wind? = null,
    var clouds: Clouds? = null,
    var dt : Int = 0,
    var sys: Sys? = null,
    var timezone : Int = 0,
    var id : Int = 0,
    var name: String? = null,
    var cod : Int = 0
)

