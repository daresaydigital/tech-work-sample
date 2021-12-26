package com.arashafsharpour.daresaymovie.infrastructure.coordinator

import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.arashafsharpour.daresaymovie.infrastructure.extensions.showSnackBar
import com.arashafsharpour.daresaymovie.infrastructure.extensions.showToast
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseViewModel

sealed class ActionCommand {
    data class ShowToast(val message: String, val duration: Int) : ActionCommand()
    data class ShowToastByResource(@StringRes val message: Int, val duration: Int) : ActionCommand()
    data class ShowSnackBar(val message: String, val duration: Int) : ActionCommand()
    data class ShowSnackBarByResource(@StringRes val message: Int, val duration: Int) :
        ActionCommand()
}

fun LifecycleOwner.observeActions(
    viewModel: BaseViewModel
) {
    viewModel.getPrimitiveCoordinator().actionCommand.observeEvent(this) { command ->
        when (command) {
            is ActionCommand.ShowToast -> when (this) {
                is ComponentActivity -> showToast(command.message, command.duration)
                is Fragment -> showToast(command.message, command.duration)
            }
            is ActionCommand.ShowToastByResource -> when (this) {
                is ComponentActivity -> showToast(command.message, command.duration)
                is Fragment -> showToast(command.message, command.duration)
            }
            is ActionCommand.ShowSnackBar -> when (this) {
                is Fragment -> showSnackBar(command.message, command.duration)
                is ComponentActivity -> {
                    Toast.makeText(this, command.message, Toast.LENGTH_SHORT).show()
                }
            }
            is ActionCommand.ShowSnackBarByResource -> when (this) {
                is Fragment ->
                    showSnackBar(command.message, command.duration)
                is ComponentActivity ->
                    findViewById<ViewGroup>(android.R.id.content).showSnackBar(
                        command.message,
                        command.duration
                    )
            }
        }
    }
}
