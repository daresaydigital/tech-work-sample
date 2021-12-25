package com.mousavi.hashem.mymoviesapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieEntity(movieEntity: MovieEntity)

    @Query("SELECT * FROM movies_table")
    fun getMovieEntities(): Flow<List<MovieEntity>>

    @Delete
    suspend fun deleteMovieEntity(movieEntity: MovieEntity)

    @Query("SELECT * FROM movies_table WHERE id=:id")
    suspend fun getMovieEntity(id: Int): MovieEntity?
}