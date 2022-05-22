package ir.sass.navigator.flow

import androidx.navigation.NavController

/**
 * this is the base class for all navigation actions
 * once you want to create a navigation you will extend this class (you can see home and movie)
 *
 * @property navigate must be overridden and you must pass a NavController to it
 */

abstract class NavigationAction{
    abstract fun navigate(navController: NavController)
}