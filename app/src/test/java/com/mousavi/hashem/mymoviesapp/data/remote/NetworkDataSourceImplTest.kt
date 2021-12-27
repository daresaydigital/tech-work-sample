package com.mousavi.hashem.mymoviesapp.data.remote

import com.google.common.truth.Truth.assertThat
import com.mousavi.hashem.common.Either
import com.mousavi.hashem.mymoviesapp.R
import com.mousavi.hashem.mymoviesapp.data.remote.dto.PageDataDto
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import retrofit2.HttpException

class NetworkDataSourceImplTest {

    private lateinit var api: Api
    private lateinit var networkDataSource: NetworkDataSource
    private lateinit var stringProvider: StringProvider

    @Before
    fun setUp() {
        api = Mockito.mock(Api::class.java)
        stringProvider = Mockito.mock(StringProvider::class.java)
        networkDataSource = NetworkDataSourceImpl(api, stringProvider)
    }

    @Test
    fun `test http exception in get popular movies`(): Unit = runBlocking {
        val exception = Mockito.mock(HttpException::class.java)
        `when`(exception.message).thenReturn("Http test message")
        `when`(api.getPopularMovies(language = "en-US", page = 1))
            .thenThrow(exception)

        val popularMovies: Either<PageDataDto, String> =
            networkDataSource.getPopularMovies(language = "en-US", page = 1)
        assertThat(popularMovies).isInstanceOf(Either.Error::class.java)
        val error = popularMovies as Either.Error
        assertThat(error.error).isEqualTo("Http test message")
    }

    @Test
    fun `test exception in get popular movies when http message is null`(): Unit = runBlocking {
        val exception = Mockito.mock(HttpException::class.java)
        `when`(exception.message).thenReturn(null)
        `when`(api.getPopularMovies(language = "en-US", page = 1))
            .thenThrow(exception)

        `when`(stringProvider.getString(R.string.error_occurred)).thenReturn("Error occurred")
        val popularMovies: Either<PageDataDto, String> =
            networkDataSource.getPopularMovies(language = "en-US", page = 1)
        assertThat(popularMovies).isInstanceOf(Either.Error::class.java)
        val error = popularMovies as Either.Error
        assertThat(error.error).isEqualTo("Error occurred")
    }
}