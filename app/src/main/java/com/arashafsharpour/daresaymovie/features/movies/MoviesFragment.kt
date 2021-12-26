package com.arashafsharpour.daresaymovie.features.movies

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.databinding.FragmentMoviesBinding
import com.arashafsharpour.daresaymovie.features.movies.adapter.MoviesListAdapter
import com.arashafsharpour.daresaymovie.infrastructure.extensions.EndlessRecyclerViewScrollListener
import com.arashafsharpour.daresaymovie.infrastructure.extensions.launch
import com.arashafsharpour.daresaymovie.infrastructure.models.MovieCategoryType
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : BaseFragment<MoviesFragmentViewModel, FragmentMoviesBinding>() {

    @Inject
    lateinit var popularAdapter: MoviesListAdapter
    @Inject
    lateinit var upcomingAdapter: MoviesListAdapter
    @Inject
    lateinit var nowPlayingAdapter: MoviesListAdapter
    @Inject
    lateinit var topRatedAdapter: MoviesListAdapter
    override val layoutResourceId: Int = R.layout.fragment_movies
    override val viewModel: MoviesFragmentViewModel by viewModels()
    private lateinit var popularEndlessScrollListener: EndlessRecyclerViewScrollListener
    private lateinit var topRatedEndlessScrollListener: EndlessRecyclerViewScrollListener
    private lateinit var upComingEndlessScrollListener: EndlessRecyclerViewScrollListener
    private lateinit var nowPlayingEndlessScrollListener: EndlessRecyclerViewScrollListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configMoviesRecyclerViews()
        configBindingClickListener()
    }

    private fun configBindingClickListener() {
        binding.profileImage.setOnClickListener {
            navController.navigate(R.id.action_moviesFragment_to_profileFragment)
        }
    }

    private fun configMoviesRecyclerViews() {
        configRefreshLayout()
        configPopularRecyclerView()
        configTopRatedRecyclerView()
        configNowPlayingRecyclerView()
        configUpcomingRecyclerView()
    }

    private fun configPopularRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        popularEndlessScrollListener =
            object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int) {
                    launch { viewModel.getPopularMovies(MovieCategoryType.POPULAR, page) }
                }
            }
        setupPopularRecyclerView(layoutManager)
    }

    private fun setupPopularRecyclerView(layoutManager: LinearLayoutManager) {
        binding.popularRecycler.apply {
            this.layoutManager = layoutManager
            adapter = this@MoviesFragment.popularAdapter
            addOnScrollListener(popularEndlessScrollListener)
        }

        popularAdapter.onItemClicked = { navigateToMovieDetails(it) }
    }

    private fun configTopRatedRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        topRatedEndlessScrollListener =
            object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int) {
                    launch { viewModel.getTopRatedMovies(MovieCategoryType.TOPRATED, page) }
                }
            }
        setupTopRatedRecyclerView(layoutManager)
    }

    private fun setupTopRatedRecyclerView(layoutManager: LinearLayoutManager) {
        binding.topRatedRecycler.apply {
            this.layoutManager = layoutManager
            adapter = this@MoviesFragment.topRatedAdapter
            addOnScrollListener(topRatedEndlessScrollListener)
        }

        topRatedAdapter.onItemClicked = { navigateToMovieDetails(it) }
    }

    private fun configUpcomingRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        upComingEndlessScrollListener =
            object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int) {
                    launch { viewModel.getPopularMovies(MovieCategoryType.UPCOMING, page) }
                }
            }
        setupUpcomingRecyclerView(layoutManager)
    }

    private fun setupUpcomingRecyclerView(layoutManager: LinearLayoutManager) {
        binding.upcomingRecycler.apply {
            this.layoutManager = layoutManager
            adapter = this@MoviesFragment.upcomingAdapter
            addOnScrollListener(upComingEndlessScrollListener)
        }
        upcomingAdapter.onItemClicked = { navigateToMovieDetails(it) }
    }

    private fun configNowPlayingRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        nowPlayingEndlessScrollListener =
            object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int) {
                    launch { viewModel.getPopularMovies(MovieCategoryType.NOWPLAYING, page) }
                }
            }
        setupNowPlayingRecyclerView(layoutManager)
    }

    private fun setupNowPlayingRecyclerView(layoutManager: LinearLayoutManager) {
        binding.nowPlayingRecycler.apply {
            this.layoutManager = layoutManager
            adapter = this@MoviesFragment.nowPlayingAdapter
            addOnScrollListener(nowPlayingEndlessScrollListener)
        }
        nowPlayingAdapter.onItemClicked = { navigateToMovieDetails(it) }
    }

    override fun initializeBindingVariables() {
        binding.vm = viewModel
    }

    private fun configRefreshLayout() {
        binding.swipeLayout.setOnRefreshListener {
            binding.swipeLayout.isRefreshing = false
            resetEndlessScrollListeners()
            clearMoviesLists()
            getMoviesByCategories()
        }
    }

    private fun clearMoviesLists() {
        viewModel.popularMovies.value = arrayListOf()
        viewModel.topRatedMovies.value = arrayListOf()
        viewModel.upcomingMovies.value = arrayListOf()
        viewModel.nowPlayingMovies.value = arrayListOf()
    }

    private fun resetEndlessScrollListeners() {
        popularEndlessScrollListener.resetState()
        topRatedEndlessScrollListener.resetState()
        upComingEndlessScrollListener.resetState()
        nowPlayingEndlessScrollListener.resetState()
    }

    private fun getMoviesByCategories() {
        viewModel.getPopularMovies(MovieCategoryType.POPULAR)
        viewModel.getTopRatedMovies(MovieCategoryType.TOPRATED)
        viewModel.getUpcomingMovies(MovieCategoryType.UPCOMING)
        viewModel.getNowPlayingMovies(MovieCategoryType.NOWPLAYING)
    }

    private fun navigateToMovieDetails(id: String) {
        val bundle = bundleOf("id" to id)
        navController.navigate(R.id.action_moviesFragment_to_movieDetailFragment, bundle)
    }
}
