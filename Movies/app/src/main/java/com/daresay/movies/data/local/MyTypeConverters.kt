package com.daresay.movies.data.local

import androidx.room.TypeConverter
import com.daresay.movies.data.models.moviedetails.Genre
import com.daresay.movies.data.models.moviedetails.Reviews
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class MyTypeConverters {
    @TypeConverter
    fun genreToJson(value: List<Genre>?): String {
        return GsonBuilder().create().toJson(value)
    }

    @TypeConverter
    fun genreFromJson(value: String): List<Genre>? {
        val type = object : TypeToken<List<Genre>>(){}.type
        return GsonBuilder().create().fromJson(value, type)
    }

    @TypeConverter
    fun reviewsToJson(value: Reviews?): String {
        return GsonBuilder().create().toJson(value)
    }

    @TypeConverter
    fun reviewsFromJson(value: String): Reviews? {
        val type = object : TypeToken<Reviews>(){}.type
        return GsonBuilder().create().fromJson(value, type)
    }
}