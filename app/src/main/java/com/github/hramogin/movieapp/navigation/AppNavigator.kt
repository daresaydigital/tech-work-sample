package com.github.hramogin.movieapp.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.github.hramogin.movieapp.R
import com.github.hramogin.movieapp.presentation.screens.moviesList.MoviesListFragmentDirections
import com.github.hramogin.movieapp.presentation.screens.splash.SplashFragment

object AppNavigator : Navigator {

    override fun toMainScreen(component: Fragment) {
        when (component) {
            is SplashFragment -> navigateTo(
                component,
                R.id.action_splashFragment_to_mainFragment
            )
        }
    }

    override fun toDetailsScreen(component: Fragment, id: String, view: View) {
        val action = MoviesListFragmentDirections.actionMoviesListFragmentToDetailsFragment(id)
        val extras = FragmentNavigatorExtras()
        navigateTo(
            component = component,
            action = action,
        )
    }

    override fun onHandleBack(component: Fragment): Boolean {
        val controller = findNavController(component)
        return controller.popBackStack()
    }


    private fun navigateTo(component: Fragment, action: Int, bundle: Bundle? = null) {
        val controller = findNavController(component)
        controller.navigate(action, bundle)
    }

    private fun navigateTo(component: Fragment, action: NavDirections) {
        findNavController(component).navigate(action)
    }
}