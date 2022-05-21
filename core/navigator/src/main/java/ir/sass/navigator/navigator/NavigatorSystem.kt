package ir.sass.navigator.navigator

import androidx.navigation.NavController
import ir.sass.navigator.flow.NavigationAction
import ir.sass.navigator.flow.features.NavcontrollerHelper


fun navigateToFlow(navController: NavcontrollerHelper, action: NavigationAction){
    navController.navigate(action)
}
