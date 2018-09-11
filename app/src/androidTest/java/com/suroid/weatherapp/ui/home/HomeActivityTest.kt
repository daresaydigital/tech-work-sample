package com.suroid.weatherapp.ui.home

import android.arch.lifecycle.MutableLiveData
import android.location.Location
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.suroid.weatherapp.R
import com.suroid.weatherapp.TestApp
import com.suroid.weatherapp.models.CityWeatherEntity
import com.suroid.weatherapp.ui.cityselection.CitySelectionActivity
import com.suroid.weatherapp.utils.TaskExecutorWithIdlingResourceRule
import com.suroid.weatherapp.utils.createCityWeather
import com.suroid.weatherapp.utils.mock
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.`when`


class HomeActivityTest {

    @Rule
    @JvmField
    val executorRule = TaskExecutorWithIdlingResourceRule()

    private var viewModel = TestApp.homeViewModel

    private val loading = MutableLiveData<Boolean>()
    private val fetchCityResult = MutableLiveData<Boolean>()
    private val cityWeatherListLiveData = MutableLiveData<ArrayList<CityWeatherEntity>>()

    @Rule
    @JvmField
    val activityRule = object : ActivityTestRule<HomeActivity>(HomeActivity::class.java) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            Mockito.`when`(viewModel.loading).thenReturn(loading)
            Mockito.`when`(viewModel.fetchCityResult).thenReturn(fetchCityResult)
            Mockito.`when`(viewModel.cityWeatherListLiveData).thenReturn(cityWeatherListLiveData)

            Mockito.`when`(TestApp.citySelectionViewModel.cityListLiveData).thenReturn(MutableLiveData())
            Mockito.`when`(TestApp.citySelectionViewModel.queryText).thenReturn(MutableLiveData())

            Mockito.`when`(TestApp.weatherCardViewModel.loadingStatus).thenReturn(MutableLiveData())
            Mockito.`when`(TestApp.weatherCardViewModel.icon).thenReturn(MutableLiveData())
            Mockito.`when`(TestApp.weatherCardViewModel.image).thenReturn(MutableLiveData())
            Mockito.`when`(TestApp.weatherCardViewModel.temp).thenReturn(MutableLiveData())
            Mockito.`when`(TestApp.weatherCardViewModel.minMaxTemp).thenReturn(MutableLiveData())
            Mockito.`when`(TestApp.weatherCardViewModel.humidity).thenReturn(MutableLiveData())
            Mockito.`when`(TestApp.weatherCardViewModel.weatherTitle).thenReturn(MutableLiveData())
            Mockito.`when`(TestApp.weatherCardViewModel.wind).thenReturn(MutableLiveData())
            Mockito.`when`(TestApp.weatherCardViewModel.city).thenReturn(MutableLiveData())
        }
    }


    @Before
    fun init() {
        Intents.init()
    }

    @Test
    fun launchActivityTest() {
        onView(ViewMatchers.withId(R.id.fab))
                .perform(click())

        intended(hasComponent(CitySelectionActivity::class.java.name))
    }

    @Test
    fun fetchCurrentWeatherTest() {
        val location = Location("")
        location.latitude = 28.530326302293503
        location.longitude = 77.21394667401911
        val locationProviderClient = Mockito.mock(FusedLocationProviderClient::class.java)
        val task = mock<Task<Location>>()
        `when`(locationProviderClient.lastLocation).thenReturn(task)
        var successListener: OnSuccessListener<Location>? = null
        `when`(task.addOnSuccessListener(any())).thenAnswer { invocation ->
            successListener = (invocation.arguments[0] as OnSuccessListener<Location>)
            task
        }
        activityRule.activity.fusedLocationClient = locationProviderClient

        cityWeatherListLiveData.postValue(ArrayList())
        onView(ViewMatchers.withId(R.id.iv_welcome)).perform(click())
        successListener?.onSuccess(location)
        Mockito.verify(viewModel).fetchForCurrentLocation(location)
    }

    @Test
    fun startLoadingTest() {
        viewModel.loading.postValue(true)
        onView(ViewMatchers.withId(R.id.progress_bar)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun stopLoadingTest() {
        viewModel.loading.postValue(false)
        onView(ViewMatchers.withId(R.id.progress_bar)).check(ViewAssertions.matches(not(isDisplayed())))
    }

    @Test
    fun addCardTest() {
        val arr = arrayListOf(createCityWeather())
        viewModel.cityWeatherListLiveData.postValue(arr)
        onView(allOf(withId(R.id.weather_card_root))).check(ViewAssertions.matches(isCompletelyDisplayed()))
    }

    @After
    fun destroy() {
        Intents.release()
    }
}