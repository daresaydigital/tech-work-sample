package com.github.hramogin.movieapp.presentation.screens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.github.hramogin.movieapp.presentation.base.viewModel.ActionLiveData
import com.github.hramogin.movieapp.presentation.base.viewModel.BaseViewModel
import com.github.hramogin.movieapp.presentation.base.viewModel.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {

    private val _openMainAction: ActionLiveData<Event<Any>> = ActionLiveData()

    val openMainScreenAction: LiveData<Event<Any>> = _openMainAction

    override fun onViewCreated() {
        super.onViewCreated()
        openRequiredScreen()
    }

    private fun openRequiredScreen() {
        viewModelScope.launch {
            delay(SPLASH_DURATION)
            _openMainAction.value = Event(Any())
        }
    }

    private companion object {
        const val SPLASH_DURATION = 3000L
    }
}