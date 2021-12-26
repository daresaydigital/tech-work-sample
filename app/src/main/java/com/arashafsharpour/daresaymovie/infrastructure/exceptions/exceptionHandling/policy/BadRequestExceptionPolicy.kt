package com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.policy

import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.infrastructure.coordinator.ICoordinator
import com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.HttpExceptionPolicy
import javax.inject.Inject

class BadRequestExceptionPolicy @Inject constructor() :
    HttpExceptionPolicy() {
    override fun getHttpCode(): Int {
        return 400
    }

    override fun handle(exception: Throwable, coordinator: ICoordinator) {
        super.handle(exception, coordinator)
        coordinator.showSnackBar(
            context.getString(R.string.error_badRequest)
        )
    }
}