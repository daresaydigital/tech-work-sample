package com.arashafsharpour.daresaymovie.infrastructure.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.launchIdling(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    GlobalIdlingResource.increment()
    val job = this.launch(context, start, block)
    job.invokeOnCompletion { GlobalIdlingResource.decrement() }
    return job
}

fun ViewModel.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(context, start, block)

fun LifecycleOwner.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = lifecycleScope.launch(context, start, block)

fun LifecycleOwner.launchWhenCreated(
    block: suspend CoroutineScope.() -> Unit
) = lifecycleScope.launchWhenCreated(block)

fun LifecycleOwner.launchWhenStarted(
    block: suspend CoroutineScope.() -> Unit
) = lifecycleScope.launchWhenStarted(block)

fun LifecycleOwner.launchWhenResumed(
    block: suspend CoroutineScope.() -> Unit
) = lifecycleScope.launchWhenResumed(block)