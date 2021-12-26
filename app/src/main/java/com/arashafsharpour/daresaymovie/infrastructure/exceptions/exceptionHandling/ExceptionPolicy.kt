package com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling

import com.arashafsharpour.daresaymovie.infrastructure.coordinator.ICoordinator

interface ExceptionPolicy {
    fun isEligible(exception: Throwable): Boolean
    fun handle(exception: Throwable, coordinator: ICoordinator)
}