package ir.sass.navigator.flow

abstract class NavigationAction(
    var navigationFlow: NavigationFlow
) {
    abstract fun navigate()
}