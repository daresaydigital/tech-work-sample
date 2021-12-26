package com.arashafsharpour.daresaymovie.features.splash

import androidx.lifecycle.MutableLiveData
import com.arashafsharpour.daresaymovie.features.splash.infrastructure.ISplashCoordinator
import com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.ExceptionHandler
import com.arashafsharpour.daresaymovie.infrastructure.extensions.launch
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Config
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseViewModel
import com.arashafsharpour.daresaymovie.persistence.repositories.config.IConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject constructor(
    val coordinator: ISplashCoordinator,
    private val configRepository: IConfigRepository,
    override val exceptionHandler: ExceptionHandler
) : BaseViewModel(coordinator) {

    private val isLoading = MutableLiveData(false)
    var isConfigRecieved = MutableLiveData(false)

    fun getConfig() = launch {
        configRepository.getConfig()
            .beforeCall { isLoading.postValue(true) }
            .afterCall { isLoading.postValue(false) }
            .onSuccess { saveConfigLocally(it) }
            .onError {
                exceptionHandler.handle(it, coordinator)
                isConfigRecieved.postValue(true)
            }
            .call()
    }

    private fun saveConfigLocally(config: Config) {
        configRepository.saveConfigLocally(config).apply { isConfigRecieved.postValue(this) }
    }
}

