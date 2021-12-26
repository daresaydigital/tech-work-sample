package com.arashafsharpour.daresaymovie.infrastructure.network

import com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.policy.NetworkTimeoutExceptionPolicy
import com.arashafsharpour.daresaymovie.persistence.USER_TOKEN
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class ApiCallHeaderInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        try {
            val builder: Request.Builder = request.newBuilder()
            val contentType = request.header("Content-Type") ?: "application/json"

            builder.addHeader("Accept", "application/json")
            builder.addHeader("Content-Type", contentType)
            builder.addHeader("Authorization", USER_TOKEN)
            return chain.proceed(builder.build())
        } catch (t: Throwable) {
            return Response.Builder()
                .request(request)
                .code(NetworkTimeoutExceptionPolicy().getHttpCode())
                .protocol(Protocol.HTTP_1_1)
                .body("{Time out}".toResponseBody(null))
                .message("Time out")
                .build()
        }
    }
}