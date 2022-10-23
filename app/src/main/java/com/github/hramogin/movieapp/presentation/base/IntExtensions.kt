package com.github.hramogin.movieapp.presentation.base

import android.content.res.Resources

val Float.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

