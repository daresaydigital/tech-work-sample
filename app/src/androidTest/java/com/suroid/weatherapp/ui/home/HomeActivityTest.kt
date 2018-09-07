package com.suroid.weatherapp.ui.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import com.suroid.weatherapp.R
import com.suroid.weatherapp.ui.cityselection.CitySelectionActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(HomeActivity::class.java, true, true)

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
}