package com.daresaydigital.presentation.feature.main.favourite

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.daresaydigital.domain.model.Movie
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.base.BaseFragment
import com.daresaydigital.presentation.feature.movie_details.MovieDetailsActivity
import com.daresaydigital.presentation.util.extensions.observeNullSafe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourite_movie.*
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@AndroidEntryPoint
class FavouriteMovieFragment : BaseFragment<FavouriteMovieViewModel>() {

    override val viewModel: FavouriteMovieViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_favourite_movie

    private var movieAdapter: FavouriteMovieAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()

        viewModel.getAllFavouriteMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieAdapter = null
    }

    private fun setupViews() {
        movieAdapter =
            FavouriteMovieAdapter(mutableListOf(),
                { movie -> onUnFavourMovieClicked(movie) },
                { movie -> adapterOnClick(movie) })
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = movieAdapter

        val divider = DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.item_seperator)!!)
        recyclerView.addItemDecoration(divider)

    }

    private fun setupObservers() {
        viewModel.favouriteMoviesViewLiveData.observeNullSafe(viewLifecycleOwner) { movieList ->
            movieList?.let {
                movieAdapter?.setData(it)

                if (it.isEmpty()){
                    tvEmpty.visibility = View.VISIBLE
                } else{
                    tvEmpty.visibility = View.GONE
                }
            }
        }
    }

    private fun onUnFavourMovieClicked(movie: Movie) {
        viewModel.unFavouriteMovie(movie.id)
    }

    private fun adapterOnClick(movie: Movie) {
        startActivity(
            MovieDetailsActivity.getLaunchIntent(requireContext(), movie)
        )
    }
}