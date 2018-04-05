package sample.network.rahul.android_weather_app.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.location.Location
import sample.network.rahul.android_weather_app.datasource.data.WeatherResponse
import sample.network.rahul.android_weather_app.datasource.remote.WeatherClient

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private var weatherResponse: LiveData<WeatherResponse>
    private var weatherClient: WeatherClient = WeatherClient.getInstance()
    private var location: MutableLiveData<Location> = MutableLiveData()
    private val query = MutableLiveData<String>()

    init {
        //weatherResponse = weatherClient.getWeather()
        weatherResponse = Transformations.switchMap(location) { search ->
            if (search == null) {
                return@switchMap MutableLiveData<WeatherResponse>()
            } else {
                return@switchMap weatherClient.getWeather(location.value!!)
            }
        }
        val l = Location("")
        l.latitude = 10.0
        l.longitude = 70.0
        location.value = l
        query.value = "hai"
    }

    fun getWeather(): LiveData<WeatherResponse> {
        return weatherResponse
    }

    fun refetchWeather(location: Location){
        this.location.value = location
    }


}