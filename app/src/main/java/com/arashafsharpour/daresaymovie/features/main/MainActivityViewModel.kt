package com.arashafsharpour.daresaymovie.features.main

import com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.ExceptionHandler
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val coordinator: IMainCoordinator,
    override val exceptionHandler: ExceptionHandler
) : BaseViewModel(coordinator)
