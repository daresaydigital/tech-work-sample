package com.daresaydigital.data.popular_movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daresaydigital.common.di.module.CoreModule
import com.daresaydigital.common.utils.GlobalDispatcher
import com.daresaydigital.data.db.AppDatabase
import com.daresaydigital.data.di.DatabaseModule
import com.daresaydigital.data.di.NetworkModule
import com.daresaydigital.data.features.popular_movie.PopularMoviesRepositoryImpl
import com.daresaydigital.data.features.popular_movie.di.PopularMoviesNetworkModule
import com.daresaydigital.data.features.popular_movie.di.PopularMoviesRepositoryModule
import com.daresaydigital.data.features.popular_movie.local.PopularMoviesLocalDataSource
import com.daresaydigital.data.features.popular_movie.remote.PopularMoviesRemoteDataSource
import com.daresaydigital.data.utils.FakeServer
import com.daresaydigital.domain.features.popular_movie.model.PopularMovies
import com.daresaydigital.domain.model.Result
import com.daresaydigital.domain.features.popular_movie.repository.PopularMoviesRepository
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.*
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(DatabaseModule::class, CoreModule::class, NetworkModule::class, PopularMoviesNetworkModule::class, PopularMoviesRepositoryModule::class)
class PopularMoviesRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: AppDatabase

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var fakeServer: FakeServer

    @Inject
    lateinit var remoteDataSource: PopularMoviesRemoteDataSource

    @Inject
    lateinit var globalDispatcher: GlobalDispatcher

    private lateinit var localDataSource: PopularMoviesLocalDataSource

    private lateinit var repository: PopularMoviesRepository

    @Before
    fun setup(){
        hiltRule.inject()
//        fakeServer.start()

        localDataSource = PopularMoviesLocalDataSource(database.popularMoviesDao(),globalDispatcher)
        repository = PopularMoviesRepositoryImpl(remoteDataSource,localDataSource)
    }

    @After
    fun teardown() {
        fakeServer.shutdown()
    }

    @Test
    fun requestAllPopularMovies_success() = runBlocking {
        // Given
        fakeServer.setHappyPopularMoviesPathDispatcher()

        // When
        val res : Result<PopularMovies> = repository.getPopularMovies(1)

        // Then
        Assert.assertTrue(res is Result.Success)
    }
}