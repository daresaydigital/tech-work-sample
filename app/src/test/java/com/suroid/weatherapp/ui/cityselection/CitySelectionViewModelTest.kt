package com.suroid.weatherapp.ui.cityselection

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.suroid.weatherapp.models.City
import com.suroid.weatherapp.repo.CityRepository
import com.suroid.weatherapp.util.RxImmediateSchedulerRule
import com.suroid.weatherapp.util.createCity
import com.suroid.weatherapp.util.matches
import com.suroid.weatherapp.util.mock
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.argThat
import org.mockito.Mockito
import java.util.function.Predicate

@RunWith(JUnit4::class)
class CitySelectionViewModelTest {
    @Rule
    @JvmField
    val schedulers = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val architectureComponentsRule = InstantTaskExecutorRule()

    private val cityRepository = mock<CityRepository>()

    private lateinit var viewModel: CitySelectionViewModel

    private val cityList = ArrayList<City>().apply {
        add(createCity())
    }

    @Before
    fun setUp() {
        Mockito.`when`(cityRepository.getAllCities()).thenReturn(Single.just(cityList))
        viewModel = CitySelectionViewModel(cityRepository)
    }

    @Test
    fun fetchCities() {
        val observer = mock<Observer<List<City>>>()
        viewModel.cityListLiveData.observeForever(observer)
        Mockito.verify(observer).onChanged(cityList)
    }

    @Test
    fun searchFailTest() {
        val observer = mock<Observer<List<City>>>()
        viewModel.cityListLiveData.observeForever(observer)
        viewModel.searchForCities("abcd")
        Mockito.verify(observer).onChanged(argThat(matches(Predicate<ArrayList<City>> {
            it.isEmpty()
        })))
    }

    @Test
    fun searchSuccessTest() {
        val observer = mock<Observer<List<City>>>()
        viewModel.cityListLiveData.observeForever(observer)
        viewModel.searchForCities("name")
        Mockito.verify(observer, Mockito.atLeastOnce()).onChanged(argThat(matches(Predicate<ArrayList<City>> {
            it.size == 1
        })))
    }

    @Test
    fun resetAfterSearchTest() {
        val observer = mock<Observer<List<City>>>()
        viewModel.cityListLiveData.value = arrayListOf()
        viewModel.cityListLiveData.observeForever(observer)
        val queryStringObserver = mock<Observer<String>>()
        viewModel.queryText.observeForever(queryStringObserver)
        viewModel.refreshData()
        Mockito.verify(observer).onChanged(argThat(matches(Predicate<ArrayList<City>> {
            it.size == 1
        })))
        Mockito.verify(queryStringObserver).onChanged("")
    }


}