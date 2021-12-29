import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.movies.tmdb.data.remote.responses.MovieObject
import com.google.common.truth.Truth.assertThat
import com.movies.tmdb.data.getOrAwaitValue
import com.movies.tmdb.data.local.MoviesDao
import com.movies.tmdb.data.local.MoviesDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var dao: MoviesDao
    private lateinit var db: MoviesDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MoviesDatabase::class.java
        ).build()
        dao = db.moviesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    fun insertShoppingItem() = runBlockingTest {

        val movieObject = MovieObject(
            false, 1, "Title Test", "Overview Test", "",
            "", "Title test", 2.8f, ""
        )
        dao.insertMovie(movieObject)

        val allShoppingItems = dao.observeAllMovies().getOrAwaitValue()

        assertThat(allShoppingItems).contains(movieObject)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val movieObject = MovieObject(
            false, 1, "Title Test", "Overview Test", "",
            "", "Title test", 2.8f, ""
        )
        dao.insertMovie(movieObject)
        dao.deleteMovie(movieObject.id)

        val allShoppingItems = dao.observeAllMovies().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(movieObject)
    }

    @Test
    fun checkIfMovieExistInDatabase() = runBlockingTest {
        val movieObject = MovieObject(
            false, 1, "Title Test", "Overview Test", "",
            "", "Title test", 2.8f, ""
        )
        dao.insertMovie(movieObject)
        val existence = dao.isMovieExistsOffline(movieObject.id)


        assertThat(existence).isTrue()
    }
}