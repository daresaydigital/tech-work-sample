package com.daresaydigital.presentation.feature.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daresaydigital.presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}