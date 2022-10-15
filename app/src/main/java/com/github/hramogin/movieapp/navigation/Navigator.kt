package com.github.hramogin.movieapp.navigation

import androidx.fragment.app.Fragment

interface Navigator {

    fun toMainScreen(component: Fragment)

    fun toDetailsScreen(component: Fragment, id: String)
}