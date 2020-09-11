package se.bynumbers.daresayweather.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.*

import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
//import com.android.volley.toolbox.
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.serialization.PrimitiveKind
import se.bynumbers.daresayweather.model.CurrentWeather
//import se.bynumbers.daresayweather.model.CurrentWeatherHelper

import se.bynumbers.daresayweather.R


val url = "http://my-json-feed"

class HttpHelper private constructor(context: Context) {
    init {
        // Init using context argument
    }

    companion object :  SingletonHolder<HttpHelper, Context>(::HttpHelper)
        val TAG = "HttpHelper"

        val requestQueue: RequestQueue by lazy {
            // applicationContext is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            Volley.newRequestQueue(context.applicationContext)
        }

        fun <T> addToRequestQueue(req: Request<T>) {
            requestQueue.add(req)
        }

        fun getCurrentWeatherJson(context : Context, url: String, callback: WeatherDataHelper)  {
            val gson = Gson()
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->

                    Log.d(TAG, "Response: ${response}")
                    val retJo  = gson.fromJson(response.toString(), JsonObject::class.java)
                    callback.onResponse(retJo)

                },
                Response.ErrorListener { error ->
                    Log.e(TAG, "Network error: ${error.message}")
                    //retJo = null
                    //TODO: On ERROR
                    Toast.makeText(context, context.getString(R.string.check_network), Toast.LENGTH_SHORT ).show()
                }
            )


            this.addToRequestQueue(jsonObjectRequest)

        }
    fun cancelAll() {
        requestQueue.cancelAll{true}
    }

}