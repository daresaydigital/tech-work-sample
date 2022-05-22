package ir.sass.daresay

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.daresay.databinding.ActivityMainBinding
import ir.sass.navigator.flow.NavigationAction
import ir.sass.navigator.flow.features.NavcontrollerHelper

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavcontrollerHelper {

    lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )
        setContentView(dataBinding.root)
    }


    override fun navigate(action: NavigationAction) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val controller = navHostFragment.navController
        action.navigate(controller)
    }

}