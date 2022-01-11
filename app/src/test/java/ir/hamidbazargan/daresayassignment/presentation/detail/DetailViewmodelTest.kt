package ir.hamidbazargan.daresayassignment.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import ir.hamidbazargan.daresayassignment.domain.repository.Repository
import ir.hamidbazargan.daresayassignment.domain.usecase.GetMovie
import ir.hamidbazargan.daresayassignment.domain.usecase.SaveMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*

class DetailViewmodelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val repository = mockk<Repository>()
    private val getMovie = spyk(GetMovie(repository))
    private val saveMovie = spyk(SaveMovie(repository))
    private lateinit var viewModel: DetailViewmodel
    private val liveDataObserver = mockk<Observer<UiModel>>()
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

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailViewmodel(
            testDispatcher,
            getMovie,
            saveMovie
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getMovie() {
        runBlockingTest {
            launch(Dispatchers.Main) {
                coEvery { repository.getMovie(1) } returns flow {
                    emit(movieEntity)
                }
                val slot = slot<UiModel>()
                val list = arrayListOf<UiModel>()
                viewModel.uiModel.observeForever(
                    liveDataObserver
                )
                every { liveDataObserver.onChanged(capture(slot)) } answers {
                    list.add(slot.captured)
                }

                viewModel.loadMovie(1)

                Assert.assertEquals(list.size, 2)
                assert(list[0] is UiModel.Loading)
                assert(list[1] is UiModel.Success)
                assert((list[1] as UiModel.Success).movie == movieEntity)

                coVerify {
                    getMovie.execute(1)
                    repository.getMovie(1)
                }
            }
        }
    }

    @Test
    fun saveMoveWithoutLoadedMovie() {

        runBlockingTest {
            launch(Dispatchers.Main) {
                coEvery { repository.saveMovie(movieEntity) } just runs
                val slot = slot<UiModel>()
                val list = arrayListOf<UiModel>()
                viewModel.uiModel.observeForever(
                    liveDataObserver
                )
                every { liveDataObserver.onChanged(capture(slot)) } answers {
                    list.add(slot.captured)
                }

                viewModel.saveMovie()

                Assert.assertEquals(list.size, 0)

                coVerify(exactly = 0) {
                    saveMovie.execute(any())
                    repository.saveMovie(any())
                }
            }
        }
    }

    @Test
    fun saveMoveWithLoadedMovie() {

        runBlockingTest {
            launch(Dispatchers.Main) {
                coEvery { repository.getMovie(1) } returns flow {
                    emit(movieEntity)
                }
                coEvery { repository.saveMovie(movieEntity) } just runs
                val slot = slot<UiModel>()
                val list = arrayListOf<UiModel>()
                viewModel.uiModel.observeForever(
                    liveDataObserver
                )
                every { liveDataObserver.onChanged(capture(slot)) } answers {
                    list.add(slot.captured)
                }

                viewModel.loadMovie(1)
                viewModel.saveMovie()

                Assert.assertEquals(list.size, 3)
                assert(list[0] is UiModel.Loading)
                assert(list[1] is UiModel.Success)
                assert(list[2] is UiModel.Bookmark)

                coVerify(exactly = 1) {
                    saveMovie.execute(movieEntity)
                    repository.saveMovie(movieEntity)
                }
            }
        }
    }
}