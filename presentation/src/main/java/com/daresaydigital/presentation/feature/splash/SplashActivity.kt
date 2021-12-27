package com.daresaydigital.presentation.feature.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.base.BaseActivity
import com.daresaydigital.presentation.feature.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()

    override fun layoutId(): Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        viewModel.onSplashViewCreated()
    }

    private fun setupViewModel() {
        viewModel.navigateToMainLiveData.observe(this) {
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        if (!isFinishing) {
            startActivity(
                MainActivity.getLaunchIntent(this).apply {
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }
            )
            finish()
        }
    }
}