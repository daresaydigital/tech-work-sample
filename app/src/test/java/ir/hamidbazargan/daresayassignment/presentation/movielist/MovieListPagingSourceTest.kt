package ir.hamidbazargan.daresayassignment.presentation.movielist

import androidx.paging.PagingSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Exception

class MovieListPagingSourceTest {

    private val dataSourceDelegate = mockk<DataSourceDelegate<List<MovieEntity>>>()
    private val pagingSource = MovieListPagingSource(dataSourceDelegate)
    private val movieEntity = MovieEntity(
        id = 3,
        title = "test3",
        originalLanguage = "test3",
        originalTitle = "test3",
        overview = "test3",
        adult = false,
        popularity = 0.1F,
        video = false,
        voteAverage = 0.1F,
        voteCount = 1,
        releaseDate = null,
        backdropPath = null,
        posterPath = null
    )

    @Test
    fun loadDataSuccessful() {
        runBlockingTest {
            coEvery { dataSourceDelegate.requestPageData(1) } returns listOf(movieEntity)

            assertEquals(
                PagingSource.LoadResult.Page(
                    data = listOf(movieEntity),
                    prevKey = null,
                    nextKey = null
                ), pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 20,
                        placeholdersEnabled = false
                    )
                )
            )

            coVerify(exactly = 1) {
                dataSourceDelegate.requestPageData(1)
            }
        }
    }

    @Test
    fun loadDataFailure() {
        runBlockingTest {
            val exception = Exception("test")
            coEvery { dataSourceDelegate.requestPageData(1) } throws exception

            assertEquals(
                PagingSource.LoadResult.Error<Int, String>(
                    exception
                ), pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 20,
                        placeholdersEnabled = false
                    )
                )
            )

            coVerify(exactly = 1) {
                dataSourceDelegate.requestPageData(1)
            }
        }
    }

}