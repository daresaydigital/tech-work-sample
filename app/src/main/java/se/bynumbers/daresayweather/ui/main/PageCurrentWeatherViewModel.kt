package se.bynumbers.daresayweather.ui.main

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject

import se.bynumbers.daresayweather.model.LocationData
import se.bynumbers.daresayweather.model.Main
import se.bynumbers.daresayweather.model.Weather
import se.bynumbers.daresayweather.util.Constants
import se.bynumbers.daresayweather.util.DateTimeHelper
import se.bynumbers.daresayweather.util.ResourceHelper
import se.bynumbers.daresayweather.R
import kotlin.math.roundToInt


class PageCurrentWeatherViewModel(application : Application) : AndroidViewModel(application) {
    private val TAG = "PageCurrentWeatherViewModel"
    private val _index = MutableLiveData<Int>()
    private val _currentWeatherImage = MutableLiveData<Drawable>()
    private val _currentWeather =
        CurrentWeatherLiveData()
    private val _currentPlace = MutableLiveData<LocationData>()
    private var _latestLocation =
        LocationData(0.0, 0.0)

    //TODO: Gör en klass, plocka ut element för element CurrentWeather
    val text: LiveData<String> = Transformations.map(_currentPlace) {
       // Log.d("PgeCurrentWeatherViewModel","Calling getForecast")
        //WeatherHelper.getForecast(application.applicationContext, it)
        //_currentWeather.getCurrentWeather(application.applicationContext, it)
        if(it == null){
            application.applicationContext.getString(R.string.ask_use_permission)
        } else {
            ""
        }
    }

    val current_label: LiveData<String> = Transformations.map(_currentWeather) {

        it["name"].asString
    }
   /* val degreeLabel : LiveData<String> = Transformations.map(_currentWeather) {
        val _context = application.applicationContext
        var degreeDrawable : Drawable? = null
        degreeDrawable = ResourceHelper.getFahrenheitOrCelsius(_context)
        degreeDrawable
    }*/
    val weatherImage: LiveData<Drawable> = Transformations.map(_currentWeather) {
       val _context = application.applicationContext
       var weatherDrawable : Drawable?
       weatherDrawable = ResourceHelper.getWeatherDrawableFromMap(_context, getWeatherIconKey(it))
       weatherDrawable
    }
    val temperature: LiveData<String> = Transformations.map(_currentWeather) {

        val gson = Gson()
        //TODO Fugly, use TypeToken instead
        val jo : JsonObject = it["main"].asJsonObject
        val main : Main = gson.fromJson(jo, Main::class.java)
        main.temp.roundToInt().toString()
    }
    private fun getWeatherIconKey(jo:JsonObject) : String {
        val gson = Gson()
        //TOOO Fix with TypeToken plz
        val ja : JsonArray = jo["weather"].asJsonArray
        val weatherMeta : Weather = gson.fromJson(ja.get(0).asJsonObject, Weather::class.java)

        var dayOrNight = if(DateTimeHelper.isDayTime()) "d" else "n"
        var key = Constants.weatherIconMap["${weatherMeta.id}$dayOrNight"]
        if(key == null){
            key = "wi_na"
        }
        return key
    }
    fun setWeatherData(currentWeather: JsonObject?){
        _currentWeather.value = currentWeather
    }
    fun updateLocation(locationData : LocationData){
        if(!locationData.equals(_latestLocation)) {
            _currentPlace.value = locationData
           _latestLocation = locationData
        }
    }
    fun setIndex(index: Int) {
        _index.value = index
    }
    fun setImage(drawable: Drawable){
        _currentWeatherImage.value = drawable
    }

}