package com.daresaydigital.data.features.favorite_movie.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "fav_movies",
    indices = [Index("id")]
)
data class FavMovieLocalEntity(
    val author: String,
    val author_details: AuthorDetailsLocal?=null,
    val content: String,
    val created_at: String,
    @PrimaryKey
    val id: String,
    val updated_at: String,
    val url: String
)