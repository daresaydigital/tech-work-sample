package com.daresaydigital.presentation.feature.main.home.popular_movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.daresaydigital.domain.model.Movie
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.base.BaseFragment
import com.daresaydigital.presentation.feature.main.home.MovieAdapter
import com.daresaydigital.presentation.util.extensions.observeNullSafe
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_popular_movie.*

@AndroidEntryPoint
class PopularMovieFragment : BaseFragment<PopularMovieViewModel>(){

    override val viewModel: PopularMovieViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_popular_movie

    private var movieAdapter : MovieAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()

        viewModel.getPopularPage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieAdapter = null
    }

    private fun setupViews(){
        movieAdapter = MovieAdapter { movie -> adapterOnClick(movie) }

        recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        recyclerView.adapter = movieAdapter
    }

    private fun setupObservers(){
        viewModel.progressVisibilityLiveData.observeNullSafe(viewLifecycleOwner){

        }

        viewModel.failureEventLiveData.observeNullSafe(viewLifecycleOwner){ pair ->
            viewFailureError(pair.first ?: getString(R.string.something_went_wrong)) {
                viewModel.getPopularPage(pair.second == 1)
            }
        }

        viewModel.movieListLiveData.observeNullSafe(viewLifecycleOwner){
            it?.let {
                movieAdapter?.submitList(it)
            }
        }
    }

    private fun viewFailureError(message: String, listener: View.OnClickListener) {
        val snackBar = Snackbar.make(
            root,
            message, Snackbar.LENGTH_LONG
        )
        snackBar.setAction(R.string.retry, listener)
        snackBar.show()
    }

    private fun adapterOnClick(movie: Movie) {

    }
}