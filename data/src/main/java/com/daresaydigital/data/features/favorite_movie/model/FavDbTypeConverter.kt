package com.daresaydigital.data.features.favorite_movie.model

import androidx.room.TypeConverter

object FavDbTypeConverter {

    @TypeConverter
    @JvmStatic
    fun genreIdListToString(genreList: List<Int>?): String? {
        val builder = StringBuilder()
        genreList?.forEach {
            builder.append(it.toString())
            builder.append("*")
        }
        return builder.toString()
    }

    @TypeConverter
    @JvmStatic
    fun stringToGenreIdList(data: String?): List<Int>? {
        try {
            if (data == null) return null
            val output = mutableListOf<Int>()
            data.split("*").forEach {
                output.add(it.toIntOrNull() ?: -1)
            }
            return output
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}