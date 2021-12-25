/**
 * Copyright (C) 2020 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arashafsharpour.daresaymovie.infrastructure.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

val Context.connectivityManager: ConnectivityManager
    get() =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.getColorFromResource(@ColorRes colorResId: Int): Int {
    return ContextCompat.getColor(this, colorResId)
}

fun Context.getDrawableFromResource(@DrawableRes resId: Int): Drawable? {
    return ContextCompat.getDrawable(this, resId)
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, duration).show()

fun Context.showToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, duration).show()