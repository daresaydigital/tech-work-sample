package com.github.hramogin.movieapp.domain.useCases.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.UnknownHostException

abstract class BaseUseCase<in Param, out Type> where Type : Any {

    abstract suspend fun run(param: Param): Either<Failure, Type>

    open val dispatcher: CoroutineDispatcher = Dispatchers.Default

    open operator fun invoke(
        scope: CoroutineScope,
        param: Param,
        result: (Either<Failure, Type>) -> Unit = {}
    ): Job {
        val backgroundJob = scope.async(dispatcher) { run(param) }
        return scope.launch(Dispatchers.Main) { result.invoke(backgroundJob.await()) }
    }

    protected fun onWrapException(exception: Exception): Failure {
        return try {
            when (exception) {
                is UnknownHostException -> Failure.NetworkFailureL(
                    exception
                )
                is ConnectException -> Failure.NetworkFailureL(
                    exception
                )
                else -> Failure.UnknownFailure(exception)
            }
        } catch (exception: Exception) {
            Failure.UnknownFailure(exception)
        }
    }
}
