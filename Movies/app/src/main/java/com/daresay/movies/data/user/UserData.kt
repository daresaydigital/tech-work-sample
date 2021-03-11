package com.daresay.movies.data.user

import android.content.Context
import android.content.SharedPreferences

class UserData(private val context: Context) {
    private fun privatePreferences(): SharedPreferences
        = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)

    // Why yes, I do realize that after implementing this, the TMDB api calls that you instructed to use in this demo
    // does not actually need a user session token. But I figured once I am employed we can scrap all the active Daresay
    // projects in favor of building a movie application. And at that point we already have the functionality for getting a session token.
    //
    // And who's laughing then?
    var sessionToken: String?
        get() = privatePreferences().getString(SESSION_TOKEN, null)
        set(value) = privatePreferences().edit().putString(SESSION_TOKEN, value).apply()

    companion object {
        private const val PREFERENCE_KEY = "ryBZOaZ8Smw8xxo"
        private const val SESSION_TOKEN = "session_token"
    }
}