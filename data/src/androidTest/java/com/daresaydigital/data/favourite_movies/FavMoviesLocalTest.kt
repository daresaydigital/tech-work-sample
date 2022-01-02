package com.daresaydigital.data.favourite_movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daresaydigital.common.di.module.CoreModule
import com.daresaydigital.common.utils.GlobalDispatcher
import com.daresaydigital.data.R
import com.daresaydigital.data.db.AppDatabase
import com.daresaydigital.data.di.DatabaseModule
import com.daresaydigital.data.di.NetworkModule
import com.daresaydigital.data.features.favorite_movie.di.FavouriteRepositoryModule
import com.daresaydigital.data.features.favorite_movie.local.FavoriteMovieLocalDataSource
import com.daresaydigital.data.features.favorite_movie.model.FavMovieLocalEntity
import com.daresaydigital.data.utils.JsonReader
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(DatabaseModule::class, CoreModule::class, NetworkModule::class, FavouriteRepositoryModule::class)
class FavMoviesLocalTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: AppDatabase

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var globalDispatcher: GlobalDispatcher

    private lateinit var favMoviesLocalDataSource: FavoriteMovieLocalDataSource

    @Before
    fun setup(){
        hiltRule.inject()

        favMoviesLocalDataSource = FavoriteMovieLocalDataSource(database.favMovieDao(),globalDispatcher)
    }

    @Test
    fun favoriteNewMovie_success() = runBlocking{
        // Given
        val fakeMovie = (gson.fromJson(JsonReader.getJson(R.raw.movie), FavMovieLocalEntity::class.java))

        // When
        favMoviesLocalDataSource.insertFavouriteMovie(fakeMovie)

        // Then
        val result = favMoviesLocalDataSource.getFavouriteMovieById(fakeMovie.id)
        Assert.assertEquals(fakeMovie.original_title, result!!.original_title)
    }

    @Test
    fun unFavouriteMovie_success() = runBlocking{
        // Given
        val fakeMovie = (gson.fromJson(JsonReader.getJson(R.raw.movie), FavMovieLocalEntity::class.java))
        favMoviesLocalDataSource.insertFavouriteMovie(fakeMovie)

        // When
        favMoviesLocalDataSource.removeFavouriteMovie(fakeMovie.id)

        // Then
        val resultMovie = favMoviesLocalDataSource.getFavouriteMovieById(fakeMovie.id)
        Assert.assertNull(resultMovie)
    }
}