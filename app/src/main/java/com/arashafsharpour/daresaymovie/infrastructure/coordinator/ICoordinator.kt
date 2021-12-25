package com.arashafsharpour.daresaymovie.infrastructure.coordinator

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar

interface ICoordinator {
    val actionCommand: MutableLiveData<Event<ActionCommand>>
    fun showError(message: String)
    fun showError(@StringRes messageResourceId: Int)
    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT)
    fun showToast(@StringRes messageResourceId: Int, duration: Int = Toast.LENGTH_SHORT)
    fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT)
    fun showSnackBar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT)
    fun tryToShowMessage(message: String?)
}