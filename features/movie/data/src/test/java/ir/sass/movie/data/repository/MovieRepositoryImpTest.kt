package ir.sass.movie.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import ir.sass.base_data.test.BaseDataTest
import ir.sass.base_domain.model.Domain
import ir.sass.movie.data.datasource.remote.DiscoverMovieApi
import ir.sass.movie.data.model.movie.DiscoverMovieDto
import ir.sass.shared_data.db.MovieDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test


class MovieRepositoryImpTest : BaseDataTest() {

    private val apiService: DiscoverMovieApi = mock()
    private val discoverMovieDto : DiscoverMovieDto = mock()
    private val movieDao : MovieDao = mock()
    private val repository = MovieRepositoryImp(apiService,movieDao)


    @ExperimentalCoroutinesApi
    @Test
    fun `api service must call discoverPopularMovies 1 time when we call discoverPopularMovies from Repository`() = testScope.runTest{
        whenever(apiService.discoverPopularMovies(1)).thenReturn(discoverMovieDto)
        repository.discoverPopularMovies(1).collect {
            verify(apiService, times(1)).discoverPopularMovies(1)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `api service must call discoverTopMovies 1 time when we call discoverPopularMovies from Repository`() = testScope.runTest{
        whenever(apiService.discoverTopMovies(1)).thenReturn(discoverMovieDto)
        repository.discoverTopMovies(1).collect {
            verify(apiService, times(1)).discoverTopMovies(1)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when you call discoverPopularMovies ,repository must emit a Progress from Domain before emitting the actual data then it must emit Result`() = testScope.runTest{
        var flagHelper = "None"
        repository.discoverPopularMovies(1).collect {
            when(flagHelper){
                "None"->{
                    assert(it is Domain.Progress)
                    flagHelper = "Progress"
                }
                "Progress"->{
                    assert(it is Domain.Data)
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when you call discoverTopMovies ,repository must emit a Progress from Domain before emitting the actual data then it must emit Result`() = testScope.runTest{
        var flagHelper = "None"
        repository.discoverTopMovies(1).collect {
            when(flagHelper){
                "None"->{
                    assert(it is Domain.Progress)
                    flagHelper = "Progress"
                }
                "Progress"->{
                    assert(it is Domain.Data)
                }
            }
        }
    }

}