package ir.sass.navigator.navigator

import ir.sass.navigator.flow.NavigationAction
import ir.sass.navigator.flow.features.NavcontrollerHelper

/**
 * this is a function for invoking navigation
 *
 * @param navController read NavcontrollerHelper for further explanations
 * @param action read NavigationAction for further explanations

 */

fun navigateToFlow(navController: NavcontrollerHelper, action: NavigationAction){
    navController.navigate(action)
}
