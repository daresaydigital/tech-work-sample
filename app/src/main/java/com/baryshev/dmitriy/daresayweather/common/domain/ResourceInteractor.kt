package com.baryshev.dmitriy.daresayweather.common.domain

import android.content.Context
import android.support.annotation.StringRes

interface IResourceInteractor {
    fun getString(@StringRes strRes: Int): String
}

class ResourceInteractor(private val context: Context) :
    IResourceInteractor {
    override fun getString(@StringRes strRes: Int): String = context.getString(strRes)
}