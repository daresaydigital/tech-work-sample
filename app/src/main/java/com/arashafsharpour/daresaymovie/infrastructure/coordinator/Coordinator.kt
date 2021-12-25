package com.arashafsharpour.daresaymovie.infrastructure.coordinator

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData

abstract class Coordinator: ICoordinator {

    override val actionCommand = MutableLiveData<Event<ActionCommand>>()

    override fun showError(message: String) {
        showSnackBar(message)
    }

    override fun showError(messageResourceId: Int) {
        showSnackBar(messageResourceId)
    }

    override fun showToast(message: String, duration: Int) {
        this.actionCommand.postValue(Event(ActionCommand.ShowToast(message, duration)))
    }

    override fun showToast(messageResourceId: Int, duration: Int) {
        this.actionCommand.postValue(
            Event(
                ActionCommand.ShowToastByResource(
                    messageResourceId,
                    duration
                )
            )
        )
    }

    override fun showSnackBar(message: String, duration: Int) {
        this.actionCommand.postValue(Event(ActionCommand.ShowSnackBar(message, duration)))
    }

    override fun showSnackBar(@StringRes message: Int, duration: Int) {
        this.actionCommand.postValue(Event(ActionCommand.ShowSnackBarByResource(message, duration)))
    }

    override fun tryToShowMessage(message: String?) {
        if (!message.isNullOrBlank()) {
            showSnackBar(message)
        }
    }
}