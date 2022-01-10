package ir.hamidbazargan.daresayassignment.presentation.movielist

import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity

interface DataSourceDelegate<T> {

    suspend fun requestPageData(page: Int): List<MovieEntity>
}