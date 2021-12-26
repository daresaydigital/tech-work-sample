package com.daresaydigital.data.features.top_rated_movie.local

import androidx.room.*
import com.daresaydigital.data.features.top_rated_movie.model.TopRatedMoviesLocalEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface TopRatedMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<TopRatedMoviesLocalEntity>)

    @Query("DELETE FROM top_rated_movies")
    suspend fun deleteAll()

    @Query("SELECT * FROM top_rated_movies")
    fun getAllTopRatedMoviesStream(): Flow<List<TopRatedMoviesLocalEntity>>
}