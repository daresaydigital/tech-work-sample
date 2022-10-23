package com.github.hramogin.movieapp.data.repository.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.hramogin.movieapp.data.database.model.MovieDb
import com.github.hramogin.movieapp.data.database.model.MovieDetailsDb
import com.github.hramogin.movieapp.data.database.model.MovieReviewsDb

@Dao
interface DaoMovie {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(list: List<MovieDb>)

    @Query("SELECT * FROM Movie")
    suspend fun getAllMovies(): List<MovieDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movie: MovieDetailsDb)

    @Query("SELECT * FROM MovieDetails WHERE MovieDetails.id == :id")
    suspend fun getMovieDetails(id: String): MovieDetailsDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovieReviews(list: List<MovieReviewsDb>)

    @Query("SELECT * FROM MovieReview")
    suspend fun getAllMovieReviews(): List<MovieReviewsDb>
}