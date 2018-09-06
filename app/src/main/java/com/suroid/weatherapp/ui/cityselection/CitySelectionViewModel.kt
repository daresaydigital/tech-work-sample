package com.suroid.weatherapp.ui.cityselection

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.suroid.weatherapp.models.City
import com.suroid.weatherapp.repo.CityRepository
import com.suroid.weatherapp.viewmodel.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @Inject Injects the required [CityRepository] in this ViewModel.
 */
class CitySelectionViewModel @Inject constructor(private val cityRepository: CityRepository) : BaseViewModel() {

    val queryText: MutableLiveData<String> = MutableLiveData()
    val cityListLiveData: MutableLiveData<List<City>> = MutableLiveData()
    private val cityList: ArrayList<City> = ArrayList()

    init {
        val cityListDisposable = cityRepository.getAllCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onCitiesFetched(it)
                }, {
                    onError(it)
                })
        compositeDisposable.add(cityListDisposable)
    }

    private fun onError(throwable: Throwable) {
        //TODO handle error case
        Log.d(CitySelectionViewModel::class.java.name, throwable.message)
    }

    private fun onCitiesFetched(cityList: List<City>) {
        this.cityList.addAll(cityList)
        cityListLiveData.value = cityList
    }

    /**
     * Resets the search query and all related states
     */
    fun refreshData() {
        queryText.value = ""
        cityListLiveData.value = cityList
    }

    /**
     * Search for the city corresponding to the query
     * @param query Query to be searched
     */
    fun searchForCities(query: String) {
        if (query.isEmpty()) {
            cityListLiveData.value = cityList
        } else {
            Observable
                    .fromIterable(cityList)
                    .filter { it.name.toLowerCase().contains(query.toLowerCase()) }
                    .toList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.single())
                    .subscribe({
                        cityListLiveData.value = it
                    }, {
                        //TODO handle error case
                    })
        }
    }
}

