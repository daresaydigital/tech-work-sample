package com.arashafsharpour.daresaymovie.infrastructure.platform

import androidx.lifecycle.ViewModel
import com.arashafsharpour.daresaymovie.infrastructure.coordinator.ICoordinator
import com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.ExceptionHandler

abstract class BaseViewModel(private val coordinator: ICoordinator) : ViewModel() {

    abstract val exceptionHandler: ExceptionHandler

    fun getPrimitiveCoordinator(): ICoordinator {
        return coordinator
    }

}