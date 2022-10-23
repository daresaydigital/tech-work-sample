package com.github.hramogin.movieapp.presentation.base.viewModel

import androidx.lifecycle.ViewModel
import com.github.hramogin.movieapp.domain.useCases.base.Failure
import timber.log.Timber

abstract class BaseViewModel: ViewModel() {

    private val _backAction: ActionLiveData<Event<Any>> = ActionLiveData()

    open fun onCreate() {}

    open fun onViewCreated() {}

    open fun onBackClicked() {
        _backAction.value = Event(Any())
    }

    open fun onHandleError(failure: Failure) {
        Timber.e(failure.exception)
        //Todo implement logic for display error on UI
    }
}