package ir.sass.navigator.navigator

import androidx.navigation.NavController
import ir.sass.navigator.flow.NavigationAction

class Navigator {
    lateinit var navController: NavController

    fun navigateToFlow(action: NavigationAction) = when (action.navigationFlow) {
        else->{

        }
    }
}