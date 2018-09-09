package com.suroid.weatherapp.ui.cityselection

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.suroid.weatherapp.models.City
import com.suroid.weatherapp.repo.CityRepository
import com.suroid.weatherapp.utils.Mockable
import com.suroid.weatherapp.viewmodel.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @Inject Injects the required [CityRepository] in this ViewModel.
 */
@Mockable
class CitySelectionViewModel @Inject constructor(cityRepository: CityRepository) : BaseViewModel() {

    val queryText: MutableLiveData<String> = MutableLiveData()
    val cityListLiveData: MutableLiveData<List<City>> = MutableLiveData()
    private val cityList: ArrayList<City> = ArrayList()
    private val searchResultsSubject = PublishSubject.create<String>()

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

        val searchDisposable = searchResultsSubject
                .debounce(200, TimeUnit.MILLISECONDS)
                .filter {
                    if (it.isEmpty()) {
                        cityListLiveData.postValue(cityList)
                        return@filter false
                    } else {
                        return@filter true
                    }
                }
                .map {
                    it.toLowerCase()
                }
                .switchMap {
                    Observable.just(cityList.filter { city ->
                        city.name.toLowerCase().contains(it)
                    })
                }
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())

        compositeDisposable.add(searchDisposable.subscribe {
            cityListLiveData.value = it
        })
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
        searchResultsSubject.onNext(query)
    }
}

