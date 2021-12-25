package com.arashafsharpour.daresaymovie.infrastructure.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.arashafsharpour.daresaymovie.R
import com.google.android.material.snackbar.Snackbar

fun Snackbar.config(context: Context) {
    val padding = context.resources.getDimensionPixelSize(R.dimen.spacing_2)
    this.view.setPadding(this.view.paddingLeft, padding, this.view.paddingRight, padding)
    this.view.background =
        ContextCompat.getDrawable(context, R.drawable.shape_small_radius_background)
    this.view.backgroundTintList =
        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.barsSnackBarA))

    val textview =
        this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textview.typeface = ResourcesCompat.getFont(context, R.font.roboto_medium)
    textview.setTextSize(
        TypedValue.COMPLEX_UNIT_PX,
        context.resources.getDimensionPixelSize(R.dimen.text_size_body2).toFloat()
    )
}