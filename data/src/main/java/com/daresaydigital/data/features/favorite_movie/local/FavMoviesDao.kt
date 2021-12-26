package com.daresaydigital.data.features.favorite_movie.local

import androidx.room.*
import com.daresaydigital.data.features.favorite_movie.model.FavMovieLocalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favMovie: FavMovieLocalEntity)

    @Query("DELETE FROM fav_movies where id=:id")
    suspend fun delete(id:Int)

    @Query("SELECT * FROM fav_movies")
    suspend fun getAllFavoriteMovies(): List<FavMovieLocalEntity>

    @Query("SELECT * FROM fav_movies")
    fun getAllFavoriteMoviesStream(): Flow<List<FavMovieLocalEntity>>

    @Query("SELECT * FROM fav_movies where id=:id")
    suspend fun getFavoriteMovieById(id:Int): FavMovieLocalEntity?
}