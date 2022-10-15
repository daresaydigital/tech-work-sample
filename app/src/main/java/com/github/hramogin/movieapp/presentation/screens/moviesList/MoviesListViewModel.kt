package com.github.hramogin.movieapp.presentation.screens.moviesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.github.hramogin.movieapp.presentation.base.viewModel.ActionLiveData
import com.github.hramogin.movieapp.presentation.base.viewModel.BaseViewModel
import com.github.hramogin.movieapp.presentation.base.viewModel.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MoviesListViewModel: BaseViewModel() {

    private val _openDetailsAction: ActionLiveData<Event<String>> = ActionLiveData()

    val openDetailsScreenAction: LiveData<Event<String>> = _openDetailsAction

    override fun onViewCreated() {
        super.onViewCreated()
        openRequiredScreen()
    }

    private fun openRequiredScreen() {
        viewModelScope.launch {
            delay(SPLASH_DURATION)
            _openDetailsAction.value = Event("id")
        }
    }

    private companion object {
        const val SPLASH_DURATION = 3000L
    }
}