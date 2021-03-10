package com.daresay.movies.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daresay.movies.data.models.movie.Movie
import com.daresay.movies.databinding.FragmentMoviesBinding
import com.daresay.movies.ui.activities.MovieDetailsActivity
import com.daresay.movies.ui.adapters.MoviePopularAdapter
import com.daresay.movies.ui.adapters.MovieTopRatedAdapter
import com.daresay.movies.ui.callbacks.MovieOnClickListener
import com.daresay.movies.ui.viewmodels.MovieListViewModel
import com.daresay.movies.utils.Resource
import com.google.android.flexbox.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviesFragment : Fragment(), MovieOnClickListener {
    private val viewModel: MovieListViewModel by viewModels()
    private lateinit var binding: FragmentMoviesBinding

    private var topRatedMoviesPage = 1
    private var topRatedMoviesPageSize = 1
    private var topRatedMoviesPageLoading = false

    private var popularMoviesPage = 1
    private var popularMoviesPageSize = 1
    private var popularMoviesPageLoading = false

    private val topRatedMoviesAdapter = MovieTopRatedAdapter(this)
    private val popularMoviesAdapter = MoviePopularAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)

        setUpTopRatedMoviesList()
        setUpPopularMoviesList()

        loadTopRatedMovies(topRatedMoviesPage)
        loadPopularMovies(popularMoviesPage)

        return binding.root
    }

    private fun setUpTopRatedMoviesList() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        binding.topRatingLoading = true
        binding.topRatedRecyclerView.layoutManager = layoutManager
        binding.topRatedRecyclerView.adapter = topRatedMoviesAdapter
        binding.topRatedRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (topRatedMoviesPage == topRatedMoviesPageSize)
                    return

                if (dx > 0) {
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    if (!topRatedMoviesPageLoading && lastVisibleItem > topRatedMoviesAdapter.itemCount - 5) {
                        topRatedMoviesPageLoading = true
                        loadTopRatedMovies(++topRatedMoviesPage)
                    }
                }
            }
        })
    }

    private fun loadTopRatedMovies(page: Int) {
        viewModel.getTopRatedMovies(page).observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { movies ->
                        topRatedMoviesAdapter.addItems(ArrayList(movies.results))
                        topRatedMoviesPageSize = movies.total_pages
                        binding.topRatingLoading = false
                        topRatedMoviesPageLoading = false
                    }
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        })
    }

    private fun setUpPopularMoviesList() {
        binding.popularLoading = true
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.justifyContent = JustifyContent.SPACE_EVENLY
        binding.popularRecyclerView.layoutManager = layoutManager
        binding.popularRecyclerView.adapter = popularMoviesAdapter
        binding.popularRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (popularMoviesPage == popularMoviesPageSize)
                    return

                if (dy > 0) {
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    if (!popularMoviesPageLoading && lastVisibleItem > popularMoviesAdapter.itemCount - 5) {
                        popularMoviesPageLoading = true
                        loadPopularMovies(++popularMoviesPage)
                    }
                }
            }
        })
    }

    private fun loadPopularMovies(page: Int) {
        viewModel.getPopularMovies(page).observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { movies ->
                        popularMoviesAdapter.addItems(ArrayList(movies.results))
                        popularMoviesPageSize = movies.total_pages
                        binding.popularLoading = false
                        popularMoviesPageLoading = false
                    }
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        })
    }

    override fun onMovieClicked(movieId: Int) {
        val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
        intent.putExtra("movie_id", movieId)
        startActivity(intent)
    }
}