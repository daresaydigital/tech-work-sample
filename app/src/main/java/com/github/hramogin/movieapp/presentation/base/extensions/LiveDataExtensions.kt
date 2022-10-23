package com.github.hramogin.movieapp.presentation.base.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.github.hramogin.movieapp.presentation.base.viewModel.Event

inline fun <T> LiveData<Event<T>>.observeEvent(
    owner: LifecycleOwner,
    crossinline onHandleEvent: (T) -> Unit
) {
    observe(owner, Observer { it?.updateIfNeed()?.let(onHandleEvent) })
}