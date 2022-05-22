package ir.sass.movie.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import ir.sass.base_data.test.BaseDataTest
import ir.sass.basedomain.model.Domain
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

    // db test is on androidTest

    @ExperimentalCoroutinesApi
    @Test
    fun `api service must call discoverMovies 1 time when we call discoverMovies from Repository`() = testScope.runTest{
        whenever(apiService.discoverMovies()).thenReturn(discoverMovieDto)
        repository.discoverMovies().collect {
            verify(apiService, times(1)).discoverMovies()
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `repository must emit a Progress from Domain before emitting the actual data then it must emit Result`() = testScope.runTest{
        var flagHelper = "None"
        repository.discoverMovies().collect {
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