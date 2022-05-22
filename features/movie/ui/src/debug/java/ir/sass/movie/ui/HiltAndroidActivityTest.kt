package ir.sass.movie.ui

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.navigator.flow.NavigationAction
import ir.sass.navigator.flow.features.NavcontrollerHelper

@AndroidEntryPoint
class HiltAndroidActivityTest : AppCompatActivity(), NavcontrollerHelper {

    override fun navigate(action: NavigationAction) {
        // nothing
    }
}