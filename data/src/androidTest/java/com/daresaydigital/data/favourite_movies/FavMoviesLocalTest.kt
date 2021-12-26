package com.daresaydigital.data.favourite_movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daresaydigital.core.di.module.CoreModule
import com.daresaydigital.core.utils.GlobalDispatcher
import com.daresaydigital.data.R
import com.daresaydigital.data.db.AppDatabase
import com.daresaydigital.data.di.DatabaseModule
import com.daresaydigital.data.di.NetworkModule
import com.daresaydigital.data.features.favorite_movie.local.FavoriteMovieLocalDataSource
import com.daresaydigital.data.features.favorite_movie.util.toLocalModel
import com.daresaydigital.data.utils.JsonReader
import com.daresaydigital.domain.features.favourite_movie.model.FavMovieDomain
import com.daresaydigital.domain.model.MovieDomain
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
@UninstallModules(DatabaseModule::class, CoreModule::class, NetworkModule::class)
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
    fun favoriteNewComic_success() = runBlocking{
        // Given
        val fakeMovie = (gson.fromJson(JsonReader.getJson(R.raw.movie), FavMovieDomain::class.java)).toLocalModel()

        // When
        favMoviesLocalDataSource.insertFavouriteMovie(fakeMovie)

        // Then
        val resultComic = favMoviesLocalDataSource.getFavouriteMovieById(fakeMovie.id)
        Assert.assertEquals(fakeMovie.original_title, resultComic!!.original_title)
    }

    @Test
    fun unFavouriteComic_success() = runBlocking{
        // Given
        val fakeMovie = (gson.fromJson(JsonReader.getJson(R.raw.movie), FavMovieDomain::class.java)).toLocalModel()

        // When
        favMoviesLocalDataSource.insertFavouriteMovie(fakeMovie)

        // Then
        val resultComic = favMoviesLocalDataSource.getFavouriteMovieById(fakeMovie.id)
        Assert.assertNull(resultComic)
    }
}