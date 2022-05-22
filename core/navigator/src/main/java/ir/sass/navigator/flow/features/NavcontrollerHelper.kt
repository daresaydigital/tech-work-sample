package ir.sass.navigator.flow.features

import ir.sass.navigator.flow.NavigationAction

/**
 * you will implement you activity with this interface
 * then you can write a proper code for navigation between modules
 *
 * @property navigate is the function that you will override
 * and write navigation codes there depending on you architecture

 */

interface NavcontrollerHelper {
    fun navigate(action: NavigationAction)
}