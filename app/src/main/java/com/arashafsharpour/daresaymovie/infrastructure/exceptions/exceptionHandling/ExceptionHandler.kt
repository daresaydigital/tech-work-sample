package com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling

import com.arashafsharpour.daresaymovie.infrastructure.coordinator.ICoordinator
import com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.policy.GeneralExceptionPolicy
import timber.log.Timber
import java.util.concurrent.CancellationException
import javax.inject.Inject

class ExceptionHandler @Inject constructor(
    private val policies: Set<@JvmSuppressWildcards ExceptionPolicy>
) {

    fun handle(
        throwable: Throwable,
        coordinator: ICoordinator,
        onExceptionListener: ((exception: Throwable, policy: ExceptionPolicy) -> Boolean)? = null
    ) {
        val eligiblePolicies = policies.filter { it.isEligible(throwable) }
        when {
            eligiblePolicies.any() -> {
                eligiblePolicies.forEach {
                    val shouldRunOriginalPolicy = onExceptionListener?.invoke(throwable, it) ?: true
                    if (shouldRunOriginalPolicy) {
                        it.handle(throwable, coordinator)
                        return
                    }
                }
            }
            throwable is CancellationException -> {
                Timber.e(throwable)
            }
            else -> {
                GeneralExceptionPolicy().handle(throwable, coordinator)
            }
        }
    }
}