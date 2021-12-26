package com.daresaydigital.data.features.popular_movie.local

import androidx.room.*
import com.daresaydigital.data.features.popular_movie.model.PopularMoviesLocalEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface PopularMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<PopularMoviesLocalEntity>)

    @Query("DELETE FROM popular_movies")
    suspend fun deleteAll()

    @Query("SELECT * FROM popular_movies")
    fun getAllPopularMoviesStream(): Flow<List<PopularMoviesLocalEntity>>
}