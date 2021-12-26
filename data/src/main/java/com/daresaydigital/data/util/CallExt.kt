package com.daresaydigital.data.util

import com.google.gson.GsonBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

/**
 * extension function that use Coroutine  to return api result model
 * i used this extension method instead of retrofit call factory to play
 * with kotlin and have more flexibility in my case study
 * @see Result
 */

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <R, V> Call<R>.callAwait(map: (R) -> V): ApiResult<V> {

    val onCancellation: (Throwable) -> Unit = {
        cancel()
    }

    /**
     * will create exception model, consider that we can develop more
     * and use sealed model which has network, io, etc models
     */
    fun createExceptionModel(error: Throwable): ErrorModel {
        return if (error is HttpException && error.response() != null) {
            GsonBuilder().create()
                .fromJson(error.response()!!.message(), ErrorModel::class.java)
        } else {
            ErrorModel(error.message)
        }
    }
    return suspendCancellableCoroutine { continuation ->
        try {
            enqueue(object : Callback<R> {
                override fun onFailure(call: Call<R>, t: Throwable) {
                    continuation.resume(
                        ApiResult.Failure(createExceptionModel(t)),
                        onCancellation
                    )
                }

                override fun onResponse(call: Call<R>, response: Response<R>) {
                    if (response.isSuccessful) {
                        try {
                            continuation.resume(
                                ApiResult.Success(map(response.body()!!)),
                                onCancellation
                            )
                        } catch (throwable: Throwable) {
                            continuation.resume(
                                ApiResult.Failure(createExceptionModel(throwable)),
                                onCancellation
                            )
                        }
                    } else {
                        continuation.resume(
                            ApiResult.Failure(ErrorModel(response.errorBody()?.string())),
                            onCancellation
                        )
                    }
                }
            })
        } catch (throwable: Throwable) {
            continuation.resume(ApiResult.Failure(createExceptionModel(throwable)), onCancellation)
        }
    }
}