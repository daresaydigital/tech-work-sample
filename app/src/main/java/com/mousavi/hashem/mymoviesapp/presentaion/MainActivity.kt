package com.mousavi.hashem.mymoviesapp.presentaion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        bottomNavigationView.setOnItemReselectedListener {
            if ((navController.currentDestination?.id == R.id.detailsFragment) ||
                navController.currentDestination?.id == R.id.reviewsFragment
            ) {
                navController.popBackStack()
            }
        }
    }

    fun selectExplorePage() {
        bottomNavigationView.selectedItemId = R.id.explore
    }

}