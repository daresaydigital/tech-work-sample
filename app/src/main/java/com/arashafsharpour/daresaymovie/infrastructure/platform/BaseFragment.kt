package com.arashafsharpour.daresaymovie.infrastructure.platform

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.arashafsharpour.daresaymovie.infrastructure.extensions.CurrentFragment


abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    protected abstract val layoutResourceId: Int
    protected open lateinit var binding: DB
    protected abstract val viewModel: VM
    lateinit var navController: NavController
    var currentBounds = MutableLiveData<Rect>()

    open fun init() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val shouldAttachToParent = false
        binding = DataBindingUtil.inflate(
            inflater,
            layoutResourceId,
            container,
            shouldAttachToParent
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        initializeBindingVariables()
    }

    open fun navigate(action: Int) {
        view?.let { _view ->
            Navigation.findNavController(_view).navigate(action)
        }
    }
    protected abstract fun initializeBindingVariables()
    open fun onNetworkStateChanged(connected: Boolean) {}
//    protected open fun setCurrentFragmentAsAmbient() {
//        CurrentFragment.currentClass = this::class.java
//        CurrentFragment.data = mapOf()
//    }

    private fun <T> getBackStackEntry(entryKey: String): T? {
        return navController.currentBackStackEntry?.savedStateHandle?.get<T>(entryKey)
    }

    fun <T> getAndRemoveBackStackEntry(entryKey: String): T? {
        val result = getBackStackEntry<T>(entryKey)
        removeBackStackEntry<T>(entryKey)
        return result
    }

    private fun <T> removeBackStackEntry(entryKey: String) {
        navController.currentBackStackEntry?.savedStateHandle?.remove<T>(entryKey)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}