package com.github.hramogin.movieapp.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieDetails")
class MovieDetailsDb(
    @PrimaryKey
    @ColumnInfo
    val id: String,
    @ColumnInfo
    val posterPath: String,
    @ColumnInfo(name = "original_title")
    val title: String,
    @ColumnInfo(name = "tagline")
    val tagline: String,
    @ColumnInfo(name = "overview")
    val overview: String,
)
