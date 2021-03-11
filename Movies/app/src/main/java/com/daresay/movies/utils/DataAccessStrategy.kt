package com.daresay.movies.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.daresay.movies.data.models.moviedetails.MovieDetails
import kotlinx.coroutines.Dispatchers
import com.daresay.movies.utils.Resource.Status.*

/**
 * Save response to database
 */
fun <T, A> performGetOperation(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }

/**
 * Reduce boilerplate code for regular network operations
 */
fun <T> performGetOperation(networkCall: suspend () -> Resource<T>) : LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            emit(responseStatus)

        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }

/**
 * Reduce boilerplate code for regular database operations
 */
fun <T> performDatabaseGetOperation(databaseQuery: () -> LiveData<T>) : LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)
    }