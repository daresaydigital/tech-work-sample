package com.mousavi.hashem.mymoviesapp.data.remote

import android.content.Context
import com.mousavi.hashem.mymoviesapp.R

class StringProviderImpl(
    private val appContext: Context,
) : StringProvider {

    override fun getHttpError() = appContext.getString(R.string.error_occurred)

    override fun getIOExceptionError() = appContext.getString(R.string.check_internet_connection)

    override fun getUnknownError() = appContext.getString(R.string.unknown_error)
}