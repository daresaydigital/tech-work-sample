package se.bynumbers.daresayweather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import se.bynumbers.daresayweather.model.CurrentWeather
//TODO
class PageForecastViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }
    val currentPlace: LiveData<String> = Transformations.map(_currentWeather) {
        "${it.name}"
    }
    fun setIndex(index: Int) {
        _index.value = index
    }

}