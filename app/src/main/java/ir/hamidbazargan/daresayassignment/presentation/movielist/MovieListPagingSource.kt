package ir.hamidbazargan.daresayassignment.presentation.movielist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity

class MovieListPagingSource(
    private val dataSourceDelegate: DataSourceDelegate<List<MovieEntity>>
) : PagingSource<Int, MovieEntity>() {
    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        try {
            val page = params.key ?: 1
            dataSourceDelegate.requestPageData(page).also {
                val nextKey = if (it.size >= 20) {
                    page + 1
                } else {
                    null
                }
                return LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = nextKey
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}