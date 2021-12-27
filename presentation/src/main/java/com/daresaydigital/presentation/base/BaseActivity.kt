package com.daresaydigital.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<V : BaseViewModel>: AppCompatActivity() {

    abstract val viewModel: V

    abstract fun layoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layoutId().takeIf { it > 0 }?.let { validLayoutId ->
            setContentView(validLayoutId)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}