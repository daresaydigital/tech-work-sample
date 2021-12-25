package com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.policy

import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.infrastructure.coordinator.ICoordinator
import com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.ExceptionPolicy
import javax.inject.Inject

class GeneralExceptionPolicy @Inject constructor() : ExceptionPolicy {
    override fun isEligible(exception: Throwable): Boolean {
        return false
    }

    override fun handle(exception: Throwable, coordinator: ICoordinator) {
        coordinator.showSnackBar(R.string.error_server)
    }
}