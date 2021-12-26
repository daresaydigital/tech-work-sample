package com.daresaydigital.data.features.movie_details.local

import androidx.room.*
import com.daresaydigital.data.features.movie_details.model.MovieDetailsLocalEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieDetailsLocalEntity)

    @Query("DELETE FROM movie_details where id=:id")
    suspend fun delete(id:Int)

    @Query("SELECT * FROM movie_details where id=:id")
    suspend fun getMovieDetailsById(id:Int): MovieDetailsLocalEntity?
}