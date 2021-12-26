package com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.policy

import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.infrastructure.coordinator.ICoordinator
import com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.HttpExceptionPolicy
import com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.exceptions.NetworkConnectionException
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import javax.inject.Inject

class NetworkTimeoutExceptionPolicy @Inject constructor() : HttpExceptionPolicy() {

    override fun getHttpCode(): Int {
        return 40
    }

    override fun isEligible(exception: Throwable): Boolean {
        return exception is SocketTimeoutException ||
                exception is NetworkConnectionException ||
                exception is SocketException ||
                exception is HttpException
    }

    override fun handle(exception: Throwable, coordinator: ICoordinator) {
        super.handle(exception, coordinator)
        coordinator.showSnackBar(context.getString(R.string.error_server)
        )
    }
}