package com.daresaydigital.presentation.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>(){

    companion object {

        fun getLaunchIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override val viewModel: MainViewModel by viewModels()

    override fun layoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        setupNavigationBottomBar()
    }

    private fun setupNavigationBottomBar(){
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_nav)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }


}