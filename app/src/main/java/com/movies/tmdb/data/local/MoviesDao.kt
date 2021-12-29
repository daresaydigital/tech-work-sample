package com.movies.tmdb.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.movies.tmdb.data.remote.responses.MovieObject

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieItem: MovieObject)

    @Query("DELETE FROM shopping_items WHERE id = :id")
    suspend fun deleteMovie(id: Int)


    @Query("SELECT * FROM shopping_items")
    fun observeAllMovies(): LiveData<List<MovieObject>>

    @Query("SELECT EXISTS (SELECT 1 FROM shopping_items WHERE id = :id)")
    suspend fun isMovieExistsOffline(id: Int): Boolean


}