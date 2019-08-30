package com.example.weatherapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.database.daos.CityDao
import com.example.weatherapp.database.entities.City
import com.example.weatherapp.utils.Event
import com.example.weatherapp.utils.runOnIoThread

class CityAddViewModel(private val cityDao: CityDao) : ViewModel() {
    private val _filteredList = MutableLiveData<List<City>>()
    val filteredList: LiveData<List<City>> = _filteredList

    val _selectCity = MutableLiveData<Event<City>>()
    val selectCity: LiveData<Event<City>> = _selectCity

    init {
        defaultResults()
    }

    private fun defaultResults() {
        runOnIoThread {
            _filteredList.postValue(cityDao.getTop())
        }
    }
    fun clearResult() {
        defaultResults()
    }
    fun filter(keyword: String) {
        runOnIoThread {
            val result = cityDao.getByName('%' + keyword + '%')
            _filteredList.postValue(result)
        }
    }


    fun handleEvent(event: CityEvent)  = when (event) {
        is CityEvent.SelectCity -> {
            _selectCity.value = Event(event.city)
        }
    }




    sealed class CityEvent {
        data class SelectCity(val city: City) : CityEvent()
    }
}