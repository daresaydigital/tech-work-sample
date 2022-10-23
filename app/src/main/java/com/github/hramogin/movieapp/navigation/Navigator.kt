package com.github.hramogin.movieapp.navigation

import android.view.View
import androidx.fragment.app.Fragment

interface Navigator {

    fun toMainScreen(component: Fragment)

    fun toDetailsScreen(component: Fragment, id: String, view: View)

    fun onHandleBack(component: Fragment): Boolean
}