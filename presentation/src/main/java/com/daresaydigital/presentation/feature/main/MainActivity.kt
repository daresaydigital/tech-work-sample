package com.daresaydigital.presentation.feature.main

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>(){

    companion object {

        fun getLaunchIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override val viewModel: MainViewModel by viewModels()

    override fun layoutId(): Int = R.layout.activity_main



}