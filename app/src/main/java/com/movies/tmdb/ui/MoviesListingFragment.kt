package com.movies.tmdb.ui

import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.movies.tmdb.R
import com.movies.tmdb.adapters.MoviesAdapter
import com.movies.tmdb.data.remote.responses.MovieObject
import com.movies.tmdb.other.Constants
import com.movies.tmdb.other.Constants.GRID_SPAN_COUNT
import com.movies.tmdb.other.Extensions.isOnline
import com.movies.tmdb.other.Extensions.toast
import com.movies.tmdb.other.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movies_listing.*
import javax.inject.Inject

@AndroidEntryPoint
class MoviesListingFragment @Inject constructor(
    val imageAdapter: MoviesAdapter
) : ParentFragment(R.layout.fragment_movies_listing) {

    private var allMovies = mutableListOf<MovieObject>()

    private var pageNum = 1
    var isLoading = false

    var selectedFilter = 0


    override fun startViewActions() {

        setupRecyclerView()
        subscribeToObservers()

        selectedFilter = Constants.FilterId.MOST_POPULAR.filterId
        getMoviesList()


        imageAdapter.setOnItemClickListener {
            findNavController().navigate(

                MoviesListingFragmentDirections.actionMoviesListingFragmentToMovieDetailsFragment(
                    selectedMovie = it
                )
            )
        }
    }


    private fun getMoviesList() = if (requireActivity().isOnline()) {
        viewModel.getMoviesListFromWeb(selectedFilter, pageNum)
        isLoading = true
    } else {
        viewModel.appBarTitle.postValue(getString(R.string.no_internet))
        requireContext().toast("No Internet connection please check internet connection")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav -> {
                findNavController().navigate(
                    MoviesListingFragmentDirections.actionMoviesListingFragmentToOfflineMoviesListingFragment()
                )
                return true
            }
            R.id.most_popular -> {
                selectedFilter = Constants.FilterId.MOST_POPULAR.filterId
                viewModel.appBarTitle.postValue(getString(R.string.most_popular))
                allMovies.clear()
                getMoviesList()

                return true
            }
            R.id.top_rated -> {
                selectedFilter = Constants.FilterId.TOP_RATED.filterId
                viewModel.appBarTitle.postValue(getString(R.string.top_rated))
                allMovies.clear()
                getMoviesList()
                return true
            }
        }
        return false
    }

    private fun subscribeToObservers() {

            viewModel.moviesList.observe(this, Observer {

                    isLoading = false
                it.getContentIfNotHandled()?.let { result ->

                    when (result.status) {
                        Status.SUCCESS -> {
                            allMovies.addAll(result.data?.results!!)
                            imageAdapter.movies = allMovies
                            progressBar.visibility = View.GONE
                        }
                        Status.ERROR -> {
                            Snackbar.make(
                                requireActivity().rootLayout,
                                result.message ?: "An unknown error occured.",
                                Snackbar.LENGTH_LONG
                            ).show()
                            progressBar.visibility = View.GONE
                        }
                        Status.LOADING -> {
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            })

    }



    private fun setupRecyclerView() {
        val gridLayoutManger = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)

        rvData.apply {
            adapter = imageAdapter
            layoutManager = gridLayoutManger
        }


        rvData.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = gridLayoutManger.childCount
                val pastVisibleItem = gridLayoutManger.findFirstCompletelyVisibleItemPosition()
                val total = imageAdapter.itemCount

                if (!isLoading) {
                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        pageNum++
                        getMoviesList()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }


}