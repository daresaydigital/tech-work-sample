package com.ivy.weatherapp.util

sealed class Failure {
    class NetworkConnection : Failure()
    class ServerError : Failure()
    class DataError : Failure()
}