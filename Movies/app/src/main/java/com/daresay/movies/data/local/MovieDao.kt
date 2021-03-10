package com.daresay.movies.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.daresay.movies.data.models.favorites.Favorite
import com.daresay.movies.data.models.favorites.FavoriteWithMovieDetails
import com.daresay.movies.data.models.moviedetails.MovieDetails

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieDetails WHERE id = :movieId")
    fun getMovie(movieId: Int) : LiveData<MovieDetails>

    @Query("SELECT * FROM Favorites WHERE movieId = :movieId")
    fun getFavorite(movieId: Int) : LiveData<Favorite>

    @Query("SELECT * FROM Favorites WHERE favorite = 1")
    fun getAllFavorites() : LiveData<List<FavoriteWithMovieDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieDetails)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)
}