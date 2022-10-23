package com.github.hramogin.movieapp.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieReview")
class MovieReviewsDb(
    @PrimaryKey
    @ColumnInfo
    val id: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @ColumnInfo(name = "author")
    val author: String,
)
