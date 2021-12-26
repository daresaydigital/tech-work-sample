package com.arashafsharpour.daresaymovie.infrastructure.extensions

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar

fun ImageView.loadFromUrl(url: String) =
    Glide.with(this.context.applicationContext)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)

fun View.showSnackBar(
    @StringRes message: Int,
    duration: Int = Snackbar.LENGTH_SHORT
) {
    showSnackBar(context.getString(message), duration)
}

@SuppressLint("ShowToast")
fun View.showSnackBar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT
) {
    val snackbar = Snackbar.make(this, message, duration)
    snackbar.config(context)
    snackbar.show()
}

fun View.getDimensionPixelSize(@DimenRes dimenResId: Int): Int {
    return context.resources.getDimensionPixelSize(dimenResId)
}