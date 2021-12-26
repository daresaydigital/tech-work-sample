package com.daresaydigital.data.model.entity

import androidx.room.Entity
import androidx.room.Index
import com.daresaydigital.data.model.AuthorDetailsLocal

@Entity(
    tableName = "fav_movies",
    indices = [Index("id")]
)
data class FavMovieLocalEntity(
    val author: String,
    val author_details: AuthorDetailsLocal,
    val content: String,
    val created_at: String,
    val id: String,
    val updated_at: String,
    val url: String
)