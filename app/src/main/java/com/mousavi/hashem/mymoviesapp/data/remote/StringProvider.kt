package com.mousavi.hashem.mymoviesapp.data.remote

import androidx.annotation.StringRes

interface StringProvider {

    fun getString(@StringRes id: Int): String
}