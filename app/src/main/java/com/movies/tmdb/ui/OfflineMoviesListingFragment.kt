package com.movies.tmdb.ui

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.movies.tmdb.R
import com.movies.tmdb.adapters.MoviesAdapter
import com.movies.tmdb.other.Constants.GRID_SPAN_COUNT
import com.movies.tmdb.other.Extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_offline_movies.*
import javax.inject.Inject

@AndroidEntryPoint
class OfflineMoviesListingFragment @Inject constructor(
    val imageAdapter: MoviesAdapter
) : ParentFragment(R.layout.fragment_offline_movies) {


    override fun startViewActions() {

        setupRecyclerView()


        getMoviesList()


        imageAdapter.setOnItemClickListener {

            findNavController().navigate(
                OfflineMoviesListingFragmentDirections.actionOfflineMoviesListingFragmentToMovieDetailsFragment(
                    selectedMovie = it
                )

            )
        }
    }


    private fun getMoviesList() {
        viewModel.shoppingItems.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                requireContext().toast(getString(R.string.no_saved))
                findNavController().popBackStack()
            } else {
                imageAdapter.movies = viewModel.shoppingItems.value!!
            }

        })
    }


    private fun setupRecyclerView() {
        val gridLayoutManger = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)

        rvData.apply {
            adapter = imageAdapter
            layoutManager = gridLayoutManger
        }
    }
}