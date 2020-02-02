package com.sneha.weatherapp.ui.dummy

import android.os.Bundle
import com.sneha.weatherapp.R
import com.sneha.weatherapp.di.component.ActivityComponent
import com.sneha.weatherapp.ui.base.BaseActivity
import com.sneha.weatherapp.ui.dummies.DummiesFragment

class DummyActivity : BaseActivity<DummyViewModel>() {

    companion object {
        const val TAG = "DummyActivity"
    }

    override fun provideLayoutId(): Int = R.layout.activity_dummy

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        addDummiesFragment()
    }

    private fun addDummiesFragment() {
        supportFragmentManager.findFragmentByTag(DummiesFragment.TAG) ?: supportFragmentManager
            .beginTransaction()
            .add(R.id.container_fragment, DummiesFragment.newInstance(), DummiesFragment.TAG)
            .commitAllowingStateLoss()
    }
}