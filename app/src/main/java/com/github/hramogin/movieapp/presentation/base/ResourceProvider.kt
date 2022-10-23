package com.github.hramogin.movieapp.presentation.base

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes
import javax.inject.Inject

interface ResourceProvider {

    fun getString(@StringRes id: Int): String
}

class ResourceProviderImpl @Inject constructor(context: Context): ResourceProvider {

    private val resources: Resources = context.resources

    override fun getString(id: Int): String {
        return resources.getString(id)
    }
}