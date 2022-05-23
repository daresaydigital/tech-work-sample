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


/*    here we have a policy, if we are in any kind of MovieListFragment
    and we click back, we must return to Home and the stack has to be cleared
    the reason is MovieListFragment is one the roots so if we click back on any root
    we should return to the first step which is Home
    if we pressed back on home even though the back stack is not empty we must
    close the program

    if we are in neither of those situation we should just pop the stack*/

    override fun onBack() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val controller = navHostFragment.navController

        when(controller.currentDestination?.label ){
            "MovieListFragment"->{
                controller.setGraph(ir.sass.navigator.R.navigation.app_navigation)
                dataBinding.bottomNavigationView.menu.findItem(R.id.home).setChecked(true)
            }

            "HomeFragment"->{
                finish()
            }

            else->{
                controller.popBackStack()
            }
        }


    }
}