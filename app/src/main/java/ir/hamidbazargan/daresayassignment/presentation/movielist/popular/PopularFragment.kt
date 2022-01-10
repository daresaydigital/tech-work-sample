package ir.hamidbazargan.daresayassignment.presentation.movielist.popular

import ir.hamidbazargan.daresayassignment.presentation.movielist.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class PopularFragment : MovieListFragment() {

    override val movieListViewModel by viewModel<MovieListViewModel>(named("popular"))
}