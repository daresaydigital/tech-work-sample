package com.daresay.movies.data.models.favorites

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.daresay.movies.data.models.moviedetails.MovieDetails

@Entity(tableName = "Favorites")
data class Favorite(
    @PrimaryKey
    val movieId: Int,
    val favorite: Boolean
)