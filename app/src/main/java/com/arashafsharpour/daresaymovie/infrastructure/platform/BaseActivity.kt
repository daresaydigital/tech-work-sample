package com.arashafsharpour.daresaymovie.infrastructure.platform

import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.window.layout.WindowMetricsCalculator
import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.infrastructure.coordinator.observeActions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseActivity<VM: BaseViewModel, VDB: ViewDataBinding> : AppCompatActivity() {

    lateinit var navController: NavController

    abstract val viewModel: VM
    abstract val layoutRes: Int
    abstract val navigationId: Int
    abstract val hasNavigation: Boolean
    lateinit var binding: VDB
    var currentBounds = MutableLiveData<Rect>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes) as VDB
        initializeBindingVariables()
        initializeNavigator()
        getScreenHeightAndWidth()
        initializeActions()
    }

    private fun initializeActions() = observeActions(viewModel)

    protected abstract fun initializeBindingVariables()
    protected open fun subscribeSpecificObservables() {}

    open fun initializeNavigator() {
        if (hasNavigation) {
            navController = (supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment).navController
            val navHostFragment =
                supportFragmentManager.findFragmentById(navigationId) as NavHostFragment?
            navController = navHostFragment!!.navController
        }
    }

    private fun getScreenHeightAndWidth() {
        lifecycleScope.launch(Dispatchers.Main) {
            val windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this@BaseActivity)
            currentBounds.value = windowMetrics.bounds  // E.g. [0 0 1350 1800]
        }
    }
}