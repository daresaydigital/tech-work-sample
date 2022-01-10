package ir.hamidbazargan.daresayassignment.presentation.movielist

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import ir.hamidbazargan.daresayassignment.domain.usecase.UseCaseWithFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MovieListViewModel(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val usecase: UseCaseWithFlow<Int>
) : ViewModel(), DataSourceDelegate<List<MovieEntity>>{

    val pagerFlow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        MovieListPagingSource(this)
    }.flow

    override suspend fun requestPageData(page: Int): List<MovieEntity> {
        return suspendCoroutine { continuation ->
            CoroutineScope(coroutineDispatcher).launch {
                try {
                    usecase.execute(page).collect { movies ->
                        continuation.resumeWith(
                            Result.success(
                                movies
                            )
                        )
                    }
                } catch (e: Exception) {
                    continuation.resumeWithException(e)
                }
            }
        }
    }
}