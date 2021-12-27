package com.daresaydigital.presentation.util.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

/**
 * simple extension for live data so we get rid of
 * null observation in our views
 */
inline fun <T> LiveData<T>.observeNullSafe(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    observe(owner) { nullableValue ->
        if (nullableValue != null) {
            observer(nullableValue)
        }
    }
}