package com.github.hramogin.movieapp.domain.useCases.base

sealed class Failure(val exception: Exception) {
    open class FeatureFailure(exception: Exception) : Failure(exception)
    open class UnknownFailure(exception: Exception) : Failure(exception)
    open class NetworkFailureL(exception: Exception) : Failure(exception)
    open class ApiFailure(val apiError: ApiError) : Failure(Exception())

    class NoDataFailure : FeatureFailure(java.lang.Exception("No data"))
}