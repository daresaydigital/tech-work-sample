package ir.sass.navigator.flow.features.home

import androidx.navigation.NavController
import ir.sass.navigator.R
import ir.sass.navigator.flow.NavigationAction

class HomeNavigationAction(
) : NavigationAction() {
    override fun navigate(navController: NavController) {
        navController.navigate(R.id.navigate_to_home)
    }
}