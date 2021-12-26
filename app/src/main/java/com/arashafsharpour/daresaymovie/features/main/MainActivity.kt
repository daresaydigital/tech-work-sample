package com.arashafsharpour.daresaymovie.features.main

import android.os.Bundle
import androidx.activity.viewModels
import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.databinding.ActivityMainBinding
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {

    override val viewModel: MainActivityViewModel by viewModels()
    override val layoutRes: Int = R.layout.activity_main
    override val navigationId: Int = R.id.main_nav_host
    override val hasNavigation: Boolean = true

    override fun initializeBindingVariables() {
        binding.vm = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
