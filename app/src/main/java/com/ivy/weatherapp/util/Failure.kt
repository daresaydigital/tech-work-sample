package com.ivy.weatherapp.util

sealed class Failure {
    class NetworkConnection : Failure()
    class ServerError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}