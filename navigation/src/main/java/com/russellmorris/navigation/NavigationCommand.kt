package com.russellmorris.navigation

import androidx.navigation.NavDirections

/**
 * A simple sealed class to aid navigation
 */
sealed class NavigationCommand {
    data class To(val directions: NavDirections) : NavigationCommand()
    object Back : NavigationCommand()
}