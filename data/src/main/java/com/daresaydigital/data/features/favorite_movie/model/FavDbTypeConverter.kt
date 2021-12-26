package com.daresaydigital.data.features.favorite_movie.model

import androidx.room.TypeConverter

object FavDbTypeConverter {
//    @TypeConverter
//    @JvmStatic
//    fun authorDetailsLocalToString(authorDetailsLocal: AuthorDetailsLocal?): String? {
//        return try {
//            val builder = StringBuilder()
//            builder.append(authorDetailsLocal?.avatar_path)
//            builder.append("*")
//            builder.append(authorDetailsLocal?.name)
//            builder.append("*")
//            builder.append(authorDetailsLocal?.rating)
//            builder.append("*")
//            builder.append(authorDetailsLocal?.username)
//            builder.toString()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//
//
//    }
//
//    @TypeConverter
//    @JvmStatic
//    fun stringToAuthorDetailsLocal(data: String?): AuthorDetailsLocal? {
//        try {
//            if (data == null) return null
//            var avatar_path = ""
//            var name = ""
//            var rating = 0.toDouble()
//            var username = ""
//            data.split("*").forEachIndexed { index, s ->
//                when (index) {
//                    0 -> {
//                        avatar_path = s
//                    }
//                    1 -> {
//                        name = s
//                    }
//                    2 -> {
//                        rating = s.toDouble()
//                    }
//                    3 ->{
//                        username = s
//                    }
//                }
//            }
//            return AuthorDetailsLocal(avatar_path, name, rating, username)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return null
//        }
//
//    }

}