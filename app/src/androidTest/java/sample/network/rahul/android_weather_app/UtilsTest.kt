package sample.network.rahul.android_weather_app

import android.location.Location
import android.support.test.runner.AndroidJUnit4
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import sample.network.rahul.android_weather_app.util.Utils
import android.support.test.InstrumentationRegistry
import com.google.gson.Gson
import sample.network.rahul.android_weather_app.datasource.data.WeatherResponse


@RunWith(AndroidJUnit4::class)
class UtilsTest {

    @Test
    fun millisecondToTime() {
        val millisecond:Long = 1522895966
        assertEquals(Utils.millisecondToTime(millisecond),"08:09 AM")
    }

    @Test
    fun getWeatherIcon() {
        val icon1 = "01d"
        assertEquals(Utils.getWeatherIcon(icon1), R.drawable.ic_sunny_2)
        val icon2 = "01n"
        assertEquals(Utils.getWeatherIcon(icon2), R.drawable.ic_moon)
    }


    @Test
    fun storeWeatherLocal() {
        val response = "{\"base\":\"stations\",\"clouds\":{\"all\":20},\"cod\":200,\"coord\":{\"lat\":78.42,\"lon\":14.08},\"dt\":1522914600,\"id\":2729907,\"main\":{\"humidity\":55.0,\"pressure\":1019.0,\"temp\":-14.0,\"temp_max\":-14.0,\"temp_min\":-14.0},\"name\":\"Longyearbyen\",\"sys\":{\"country\":\"SJ\",\"id\":5326,\"message\":0.1748,\"sunrise\":1522895966,\"sunset\":1522957189,\"type\":1},\"visibility\":10000,\"weather\":[{\"description\":\"few clouds\",\"icon\":\"02d\",\"id\":801,\"main\":\"Clouds\"}],\"wind\":{\"deg\":110.0,\"speed\":6.2}}"
        val instrumentationContext = InstrumentationRegistry.getContext()
        Utils.storeWeatherLocal(instrumentationContext, Gson().fromJson(response, WeatherResponse::class.java))
        assertEquals(Utils.fetchWeatherFromLocal(instrumentationContext)!!.name,"Longyearbyen" )
        assertEquals(Utils.fetchWeatherFromLocal(instrumentationContext)!!.main!!.temp,-14.0 )
    }


    @Test
    fun storeLastLocationLocal() {
        var location = Location("test")
        location.latitude = 10.00
        location.longitude = 73.00
        val instrumentationContext = InstrumentationRegistry.getContext()
        Utils.storeLastLocationLocal(instrumentationContext,location)
        assertEquals(Utils.fetchLastLocationFromLocal(instrumentationContext)!!.latitude,10.00,0.0)
        assertEquals(Utils.fetchLastLocationFromLocal(instrumentationContext)!!.longitude,73.00,0.0)
    }
}