package com.mousavi.hashem.mymoviesapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mousavi.hashem.mymoviesapp.domain.model.Movie

@Entity(tableName = "movies_table")
class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val backdropPath: String?,
    val genreIds: List<String>,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
) {
    fun toMovie(): Movie {
        return Movie(
            backdropPath = backdropPath,
            genreNames = genreIds.toMutableList(),
            id = id,
            overview = overview,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            voteAverage = voteAverage,
            voteCount = voteCount,
        )
    }
}