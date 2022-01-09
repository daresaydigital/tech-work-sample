package ir.hamidbazargan.daresayassignment.data.db

import androidx.room.*
import ir.hamidbazargan.daresayassignment.data.db.entity.MovieDataBaseEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY row DESC")
    fun queryMovies(): List<MovieDataBaseEntity>

    @Query("SELECT * FROM movies WHERE id == :id")
    fun queryMovie(id: Int): MovieDataBaseEntity?

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    fun insertMovie(movieEntities: MovieDataBaseEntity)

    @Delete
    fun deleteMovie(movieEntities: MovieDataBaseEntity)
}