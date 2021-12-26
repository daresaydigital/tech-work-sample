package com.arashafsharpour.daresaymovie.infrastructure.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) =
    context?.showToast(message, duration)

fun Fragment.showToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) =
    context?.showToast(message, duration)

fun Fragment.showSnackBar(
    @StringRes message: Int,
    duration: Int = Snackbar.LENGTH_SHORT
) = view?.showSnackBar(message, duration)

fun Fragment.showSnackBar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT
) = view?.showSnackBar(message, duration)