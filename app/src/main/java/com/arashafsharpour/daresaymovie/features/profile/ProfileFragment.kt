package com.arashafsharpour.daresaymovie.features.profile

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.databinding.FragmentProfileBinding
import com.arashafsharpour.daresaymovie.features.profile.adapter.ProfileMovieAdapter
import com.arashafsharpour.daresaymovie.infrastructure.extensions.EndlessRecyclerViewScrollListener
import com.arashafsharpour.daresaymovie.infrastructure.extensions.launch
import com.arashafsharpour.daresaymovie.infrastructure.models.MovieCategoryType
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentViewModel, FragmentProfileBinding>() {

    override val layoutResourceId: Int = R.layout.fragment_profile
    override val viewModel: ProfileFragmentViewModel by viewModels()
    @Inject
    lateinit var favoriteMovieAdapter: ProfileMovieAdapter
    @Inject
    lateinit var watchLaterMovieAdapter: ProfileMovieAdapter
    private lateinit var favoriteEndlessScrollListener: EndlessRecyclerViewScrollListener
    private lateinit var watchLaterEndlessScrollListener: EndlessRecyclerViewScrollListener

    override fun initializeBindingVariables() {
        binding.profile = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configBindingClickListener()
        configFavoriteRecyclerView()
        configWatchlistRecyclerView()
    }

    private fun configBindingClickListener() {
        binding.back.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun configFavoriteRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        favoriteEndlessScrollListener =
            object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int) {
                    launch { viewModel.getFavoriteMovies(MovieCategoryType.FAVORITE, page) }
                }
            }
        setupFavoriteRecyclerView(layoutManager)
    }

    private fun setupFavoriteRecyclerView(layoutManager: LinearLayoutManager) {
        binding.favoriteRecycler.apply {
            this.layoutManager = layoutManager
            adapter = this@ProfileFragment.favoriteMovieAdapter
            addOnScrollListener(favoriteEndlessScrollListener)
        }

        favoriteMovieAdapter.onItemClicked = { navigateToMovieDetails(it) }
    }

    private fun configWatchlistRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        watchLaterEndlessScrollListener =
            object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int) {
                    launch { viewModel.getWatchLaterMovies(MovieCategoryType.WATCHLATER, page) }
                }
            }
        setupTopRatedRecyclerView(layoutManager)
    }

    private fun setupTopRatedRecyclerView(layoutManager: LinearLayoutManager) {
        binding.watchlistRecycler.apply {
            this.layoutManager = layoutManager
            adapter = this@ProfileFragment.watchLaterMovieAdapter
            addOnScrollListener(watchLaterEndlessScrollListener)
        }

        watchLaterMovieAdapter.onItemClicked = { navigateToMovieDetails(it) }
    }

    private fun navigateToMovieDetails(id: String) {
        val bundle = bundleOf("id" to id)
        navController.navigate(R.id.action_profileFragment_to_movieDetailFragment, bundle)
    }
}
