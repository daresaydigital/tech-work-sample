package ir.sass.movie.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import ir.sass.base_data.test.BaseDataTest
import ir.sass.base_domain.model.Domain
import ir.sass.movie.data.datasource.remote.DiscoverMovieApi
import ir.sass.movie.data.repository.MovieRepositoryImp
import ir.sass.shared_data.db.AppDatabase
import ir.sass.shared_data.db.MovieDao
import ir.sass.shared_data.db.model.ResultEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieRepositoryImpTest : BaseDataTest() {

    private val apiService: DiscoverMovieApi = mock()
    private lateinit var movieDao: MovieDao
    private lateinit var repository: MovieRepositoryImp
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        movieDao = db.movieDao()

        repository = MovieRepositoryImp(apiService, movieDao)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun if_we_call_saveToLocal_from_repository_then_there_must_be_a_data_in_db() =
        testScope.runTest {
            val data = insertAStubIntoDb()
            val response = movieDao.getAllResults()
            response.collect {
                assertThat(it[0], equalTo(data))
            }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun if_we_call_deleteFromLocal_from_repository_then_that_row_should_be_deleted_from_db() =
        testScope.runTest {
            val data = insertAStubIntoDb()
            movieDao.deleteAResult(data.id)
            val response = movieDao.getAllResults()
            response.collect {
                assert(it.isEmpty())
            }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun if_we_call_discoverMoviesFromLocal_from_repository_then_we_must_get_a_flow_of_DiscoverMovieModel_with_Domain_wrapper() =
        testScope.runTest {
            val data = insertAStubIntoDb()
            repository.discoverMoviesFromLocal().collect {
                assert(it is Domain.Data)
                assert((it as Domain.Data).data.isSuccess)
                assert((it as Domain.Data).data.getOrThrow().results!!.size == 1)
            }
        }

    private fun insertAStubIntoDb(): ResultEntity {
        val data = createEntityStub()
        movieDao.insertNewResult(createEntityStub())
        return data
    }

    private fun createEntityStub() = ResultEntity(
        true, 1, "1", "1", "1", 1.0,
        "1", "1", "1", "1", 1.0, 1
    )


}