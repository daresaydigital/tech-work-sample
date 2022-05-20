package ir.sass.navigator.navigator

import androidx.navigation.NavController
import ir.sass.navigator.flow.NavigationAction


fun navigateToFlow(navController: NavController,action: NavigationAction){
    action.navigate(navController)
}
