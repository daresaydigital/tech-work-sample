package se.bynumbers.daresayweather.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Response
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.google.gson.JsonObject
import se.bynumbers.daresayweather.model.LocationData
import se.bynumbers.daresayweather.util.WeatherDataHelper

class CurrentWeatherLiveData : MutableLiveData<JsonObject>(), Response.Listener<JsonObject>,
    Response.ErrorListener {

    override fun onResponse(response: JsonObject?) {
        value = response
    }

    override fun onErrorResponse(error: VolleyError?) {
        TODO("Not yet implemented")
    }

}

class CurrentWeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "CurrentWeatherViewModel"
    private var currentWeatherData = MutableLiveData<JsonObject>()

    //TODO Singleton
    private val wdHelper = WeatherDataHelper()

    fun getCurrentWeather(context: Context, locationData: LocationData) : MutableLiveData<JsonObject> {
        currentWeatherData = wdHelper.getForecast(context, locationData)
        return currentWeatherData
    }
    //

}