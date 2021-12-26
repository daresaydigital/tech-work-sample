package com.daresaydigital.data.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "movies",
    indices = [Index("id")]
)
data class MovieLocalEntity(
    val author: String,
    val author_details: AuthorDetailsLocalEntity,
    val content: String,
    val created_at: String,
    val id: String,
    val updated_at: String,
    val url: String
)