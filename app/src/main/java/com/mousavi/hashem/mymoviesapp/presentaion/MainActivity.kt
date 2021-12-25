package com.mousavi.hashem.mymoviesapp.presentaion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mousavi.hashem.mymoviesapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigation()

    }

    private fun setupBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.main_nav_host
        ) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnItemReselectedListener { menuItem ->
            if (menuItem.itemId == R.id.explore) {
                if ((navController.currentDestination?.id == R.id.detailsFragment)) {
                    navController.popBackStack()
                }
            } else if (menuItem.itemId == R.id.favorite) {
                if ((navController.currentDestination?.id == R.id.detailsFragment)) {
                    navController.popBackStack()
                }
            }
        }
    }

    fun selectExplorePage() {
        bottomNavigationView.selectedItemId = R.id.explore
    }

}