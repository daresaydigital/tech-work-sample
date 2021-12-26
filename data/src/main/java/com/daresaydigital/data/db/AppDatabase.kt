package com.daresaydigital.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.daresaydigital.data.features.favorite_movie.local.FavMoviesDao
import com.daresaydigital.data.features.favorite_movie.model.FavDbTypeConverter
import com.daresaydigital.data.features.movie_details.local.MovieDetailsDao
import com.daresaydigital.data.features.favorite_movie.model.FavMovieLocalEntity
import com.daresaydigital.data.features.movie_details.model.MovieDetailsDbTypeConverter
import com.daresaydigital.data.features.movie_details.model.MovieDetailsLocalEntity
import com.daresaydigital.data.features.popular_movie.local.PopularMoviesDao
import com.daresaydigital.data.features.popular_movie.model.PopularMoviesLocalEntity
import com.daresaydigital.data.features.top_rated_movie.local.TopRatedMoviesDao
import com.daresaydigital.data.features.top_rated_movie.model.TopRatedMoviesLocalEntity

@Database(entities = [FavMovieLocalEntity::class, MovieDetailsLocalEntity::class, TopRatedMoviesLocalEntity::class, PopularMoviesLocalEntity::class], version = 1)
@TypeConverters(FavDbTypeConverter::class, MovieDetailsDbTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favMovieDao(): FavMoviesDao

    abstract fun movieDetailsDao(): MovieDetailsDao

    abstract fun topRatedMoviesDao(): TopRatedMoviesDao

    abstract fun popularMoviesDao(): PopularMoviesDao
}