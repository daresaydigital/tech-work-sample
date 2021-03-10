package com.daresay.movies.data.models.favorites

import androidx.room.Embedded
import androidx.room.Relation
import com.daresay.movies.data.models.moviedetails.MovieDetails

data class FavoriteWithMovieDetails (
    @Embedded val favorite: Favorite,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "id"
    )
    val movieDetails: MovieDetails
)