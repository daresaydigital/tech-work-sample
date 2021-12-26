package com.daresaydigital.data.features.movie_details

import androidx.room.*
import com.daresaydigital.data.model.entity.MovieDetailsLocalEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailsLocalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieDetailsLocalEntity)

    @Query("DELETE FROM movie_details where id=:id")
    suspend fun delete(id:Int)

    @Query("SELECT * FROM movie_details where id=:id")
    suspend fun getMovieDetailsById(id:Int): Flow<MovieDetailsLocalEntity?>
}