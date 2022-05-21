package ir.sass.daresay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.daresay.databinding.ActivityMainBinding
import ir.sass.navigator.flow.features.NavcontrollerFinder

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , NavcontrollerFinder {

    lateinit var dataBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )
        setContentView(dataBinding.root)
    }

    override fun getGlobalNavcontroller(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

}