/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.suroid.weatherapp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.suroid.weatherapp.ui.cityselection.CitySelectionActivity
import com.suroid.weatherapp.ui.cityselection.CitySelectionViewModel
import com.suroid.weatherapp.ui.home.HomeActivity
import com.suroid.weatherapp.ui.home.HomeViewModel
import com.suroid.weatherapp.ui.weathercards.WeatherCardFragment
import com.suroid.weatherapp.ui.weathercards.WeatherCardViewModel
import com.suroid.weatherapp.utils.ViewModelUtil
import org.mockito.Mockito

/**
 * We use a separate App for tests to prevent initializing dependency injection.
 *
 */
class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(object: ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                when (activity) {
                    is HomeActivity -> activity.viewModelFactory = ViewModelUtil.createFor(homeViewModel)
                    is CitySelectionActivity -> activity.viewModelFactory = ViewModelUtil.createFor(citySelectionViewModel)
                }

                if (activity is FragmentActivity) {
                    activity.supportFragmentManager
                            .registerFragmentLifecycleCallbacks(
                                    object : FragmentManager.FragmentLifecycleCallbacks() {
                                        override fun onFragmentCreated(
                                                fm: FragmentManager,
                                                f: Fragment,
                                                savedInstanceState: Bundle?
                                        ) {
                                            if (f is WeatherCardFragment) {
                                                f.viewModelFactory = ViewModelUtil.createFor(weatherCardViewModel)
                                            }
                                        }
                                    }, true
                            )
                }
            }

        })

    }
    companion object {
        val homeViewModel: HomeViewModel by lazy { Mockito.mock(HomeViewModel::class.java) }
        val citySelectionViewModel: CitySelectionViewModel by lazy { Mockito.mock(CitySelectionViewModel::class.java) }
        val weatherCardViewModel: WeatherCardViewModel by lazy { Mockito.mock(WeatherCardViewModel::class.java) }
    }
}
