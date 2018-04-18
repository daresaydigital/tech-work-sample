package com.baryshev.dmitriy.daresayweather.utils.extensions

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.design.widget.Snackbar
import android.support.graphics.drawable.Animatable2Compat
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.baryshev.dmitriy.daresayweather.R
import com.baryshev.dmitriy.daresayweather.utils.loge

inline fun Context.makeErrorSnackBar(parent: View,
                                     error: Throwable?,
                                     crossinline action: () -> Unit,
                                     text: String = getString(R.string.error_common)
                                    ): Snackbar {
    loge("Error!",
         "expand error",
         error ?: UnknownError())

    return Snackbar.make(parent, text, Snackbar.LENGTH_INDEFINITE)
        .setAction(getString(R.string.main_snackbar_action_retry), { action() })
        .setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))

}

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

inline fun ImageView.morphIcon(crossinline onAnimationEndCallback: () -> Unit = {}): Drawable {

    return this.drawable.apply {
        if (this is AnimatedVectorDrawableCompat) {
            registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    super.onAnimationEnd(drawable)
                    onAnimationEndCallback()
                }
            })
            start()
        }
    }
}