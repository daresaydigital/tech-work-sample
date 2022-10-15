package com.github.hramogin.movieapp.presentation.base.extensions

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.github.hramogin.movieapp.presentation.base.viewModel.Event

inline fun <T> LiveData<Event<T>>.observeEvent(
    owner: LifecycleOwner,
    crossinline onHandleEvent: (T) -> Unit
) {
    observe(owner, Observer { it?.updateIfNeed()?.let(onHandleEvent) })
}

inline fun <T> LiveData<T>.observeNotNull(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    observe(owner, Observer { it?.let{ observer.invoke(it)} })
}

fun <T> LiveData<T>.debounce(debounce: Long = 1000L) = MediatorLiveData<T>().also { mld ->
    val source = this
    val handler = Handler(Looper.getMainLooper())

    val runnable = Runnable {
        mld.value = source.value
    }

    mld.addSource(source) {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, debounce)
    }
}