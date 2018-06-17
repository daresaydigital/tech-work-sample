package com.ivy.weatherapp.util

sealed class Failure {
    class Network : Failure()
    class Server : Failure()
    class Data : Failure()
}