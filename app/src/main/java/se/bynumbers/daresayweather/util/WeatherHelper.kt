package se.bynumbers.daresayweather.util

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import com.android.volley.Response
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.google.gson.JsonObject

import se.bynumbers.daresayweather.model.LocationData
import se.bynumbers.daresayweather.util.Constants.Companion.WEATHER_URL
import se.bynumbers.daresayweather.R
import java.io.InputStream
import java.util.*

//TODO Should refactor this to listener...when time.
class WeatherDataHelper : MutableLiveData<JsonObject?>(), Response.Listener<JsonObject>,

    Response.ErrorListener {
    var  weatherData = MutableLiveData<JsonObject>()

    override fun onErrorResponse(error: VolleyError?) {

    }

    override fun onResponse(response: JsonObject) {
        //if(response CurrentWeather){
        //TODO!! Change type here (Fix Json->Pojo
           weatherData.value = response
    }
    fun getForecast(context: Context, locationData : LocationData) : MutableLiveData<JsonObject> {

        val apiKey = getApiKey(context)

        val url =
            "${WEATHER_URL}?lat=${locationData.latitude}&lon=${locationData.longitude}&key=$apiKey"
        HttpHelper.getInstance(context).getCurrentWeatherJson(context, url, this)
        return weatherData
    }

    private fun getApiKey(
        context: Context
    ): String? {

         val resources: Resources = context.resources
         val TAG = "WeatherHelper"
        //TODO: Cache api-key in SecurePrefs?
         try {
             val rawResource: InputStream = resources.openRawResource(R.raw.config)
             val properties = Properties()
             properties.load(rawResource)
             return properties.getProperty(Constants.API_KEY)
         } catch (e: Exception) {
             Log.e(TAG, "Unable to aquire api-key: " + e.message)
             Toast.makeText(context, context.getString(R.string.no_config_file), Toast.LENGTH_SHORT ).show()
         }
        return ""
    }
}





