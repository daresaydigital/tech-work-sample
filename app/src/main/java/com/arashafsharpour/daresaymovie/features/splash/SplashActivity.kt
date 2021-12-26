package com.arashafsharpour.daresaymovie.features.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.databinding.ActivitySplashBinding
import com.arashafsharpour.daresaymovie.features.main.MainActivity
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    override val viewModel: SplashViewModel by viewModels()
    override val layoutRes: Int = R.layout.activity_splash
    override val navigationId: Int = 0
    override val hasNavigation: Boolean = false

    override fun initializeBindingVariables() {
        binding.vm = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeToViewModel()
        viewModel.getConfig()
    }

    private fun observeToViewModel() {
        viewModel.isConfigRecieved.observe(this) {
            if (it) relocateToMainActivity()
        }
    }

    private fun relocateToMainActivity() {
        Intent().apply {
            intent.setClass(this@SplashActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
