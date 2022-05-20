package ir.sass.navigator.flow

import androidx.navigation.NavController

abstract class NavigationAction{
    abstract fun navigate(navController: NavController)
}