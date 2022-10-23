package com.github.hramogin.movieapp.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
class MovieDb(
    @PrimaryKey
    @ColumnInfo
    val id: String,

    @ColumnInfo
    val posterPath: String,

    @ColumnInfo(name = "title")
    val title: String,
)
