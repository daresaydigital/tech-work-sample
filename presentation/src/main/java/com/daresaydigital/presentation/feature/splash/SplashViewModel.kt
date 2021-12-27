package com.daresaydigital.presentation.feature.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.daresaydigital.core.utils.GlobalDispatcher
import com.daresaydigital.presentation.base.BaseViewModel
import com.daresaydigital.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val globalDispatcher: GlobalDispatcher
): BaseViewModel(){

    private companion object {
        const val SPLASH_DELAY_MS = 2000L
    }

    private val _navigateToMainLiveData = SingleLiveEvent<Unit>()
    val navigateToMainLiveData: LiveData<Unit> = _navigateToMainLiveData

    fun onSplashViewCreated() {
        viewModelScope.launch(globalDispatcher.io) {
            delay(SPLASH_DELAY_MS)
            withContext(globalDispatcher.main) {
                _navigateToMainLiveData.call()
            }
        }
    }

}