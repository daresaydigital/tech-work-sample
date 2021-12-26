package com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling

import android.content.Context
import android.util.Log
import androidx.annotation.CallSuper
import com.arashafsharpour.daresaymovie.infrastructure.coordinator.ICoordinator
import com.fasterxml.jackson.databind.ObjectMapper
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

abstract class HttpExceptionPolicy : ExceptionPolicy {

    @Inject
    lateinit var objectMapper: ObjectMapper

    @Inject
    lateinit var context: Context

    var errorBody: String? = null
    override fun isEligible(exception: Throwable): Boolean {
        return exception is HttpException && exception.code() == getHttpCode()
    }

    abstract fun getHttpCode(): Int

    @CallSuper
    override fun handle(exception: Throwable, coordinator: ICoordinator) {
        errorBody = parseError(exception as HttpException)
    }

    private fun parseError(e: HttpException): String? {
        if (e.response() != null && e.response()!!.errorBody() != null) {
            try {
                return e.response()!!.errorBody()!!.string()
            } catch (e: java.lang.Exception) {
                Timber.e(e)
            }
        }
        return null
    }
}
