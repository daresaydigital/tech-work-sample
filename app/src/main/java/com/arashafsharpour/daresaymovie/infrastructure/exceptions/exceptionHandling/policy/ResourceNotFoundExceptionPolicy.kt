package com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.policy

import com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.HttpExceptionPolicy
import javax.inject.Inject

class ResourceNotFoundExceptionPolicy @Inject constructor() : HttpExceptionPolicy() {
    override fun getHttpCode(): Int {
        return 404
    }
}