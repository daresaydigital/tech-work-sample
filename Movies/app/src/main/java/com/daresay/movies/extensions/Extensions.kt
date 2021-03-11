package com.daresay.movies.extensions

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.snack(@StringRes textRes: Int, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, textRes, length).show()
}