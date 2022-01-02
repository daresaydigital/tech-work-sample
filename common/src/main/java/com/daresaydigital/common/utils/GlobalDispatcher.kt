package com.daresaydigital.common.utils

import kotlinx.coroutines.CoroutineDispatcher

/**
 * this class will be passed to view models as a dependency so we
 * have testable coroutines
 */
data class GlobalDispatcher(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher
)