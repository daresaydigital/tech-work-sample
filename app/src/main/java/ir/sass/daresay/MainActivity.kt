package ir.sass.daresay

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.daresay.databinding.ActivityMainBinding
import ir.sass.navigator.flow.NavigationAction
import ir.sass.navigator.flow.features.NavcontrollerHelper
import ir.sass.navigator.flow.features.home.HomeNavigationAction
import ir.sass.navigator.flow.features.movie.MovieNavigationAction
import ir.sass.navigator.navigator.navigateToFlow
import ir.sass.shared_domain.MovieListType

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavcontrollerHelper {

    lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )
        setContentView(dataBinding.root)

        dataBinding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->{
                    navigateToFlow(this, HomeNavigationAction())
                }
                R.id.popular-> {
                    navigateToFlow(this,MovieNavigationAction(MovieListType.POPULAR.ordinal))
                }
                R.id.favorite-> {
                    navigateToFlow(this,MovieNavigationAction(MovieListType.FAVORITE.ordinal))
                }
                R.id.top_rated-> {
                    navigateToFlow(this,MovieNavigationAction(MovieListType.TOP_RATED.ordinal))
                }
            }
            true
        }
    }


    override fun navigate(action: NavigationAction) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val controller = navHostFragment.navController
        action.navigate(controller)
    }

}