package com.github.hramogin.movieapp.presentation.base.viewModel

import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    private val _backAction: ActionLiveData<Event<Any>> = ActionLiveData()

    open fun onCreate() {}

    open fun onViewCreated() {}

    open fun onBackClicked() {
        _backAction.value = Event(Any())
    }
}