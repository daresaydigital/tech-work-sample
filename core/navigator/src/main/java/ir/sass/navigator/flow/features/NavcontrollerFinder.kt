package ir.sass.navigator.flow.features

import androidx.navigation.NavController

interface NavcontrollerFinder {
    fun getGlobalNavcontroller() : NavController
}