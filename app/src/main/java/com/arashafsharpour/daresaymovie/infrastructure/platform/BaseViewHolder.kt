package com.arashafsharpour.daresaymovie.infrastructure.platform

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.cancelChildren

abstract class BaseViewHolder(binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root), LifecycleOwner {

    abstract val lifecycleRegistry: LifecycleRegistry

    open fun onAppear() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    open fun onDisappear() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        lifecycleScope.coroutineContext.cancelChildren()
    }

    open fun setVariable(item: Any?) {

    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}