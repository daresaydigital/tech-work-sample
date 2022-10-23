package com.github.hramogin.movieapp.presentation.screens.moviesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.hramogin.movieapp.R
import com.github.hramogin.movieapp.databinding.FragmentMoviesListBinding
import com.github.hramogin.movieapp.navigation.AppNavigator
import com.github.hramogin.movieapp.presentation.base.BaseFragment
import com.github.hramogin.movieapp.presentation.base.extensions.observeEvent
import com.github.hramogin.movieapp.presentation.screens.moviesList.adapter.MovieItemDecorator
import com.github.hramogin.movieapp.presentation.screens.moviesList.adapter.MoviesListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListFragment : BaseFragment<MoviesListViewModel, FragmentMoviesListBinding>() {

    override val viewModel: MoviesListViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.fragment_movies_list

    private lateinit var adapter: MoviesListAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMoviesListBinding =
        { inflater, container, attached ->
            FragmentMoviesListBinding.inflate(inflater, container, attached)
        }

    private fun initViews() {
        adapter = MoviesListAdapter { movie, view ->
            AppNavigator.toDetailsScreen(this, movie.id, view)
        }
        binding.rwMoviesList.adapter = adapter
        binding.rwMoviesList.addItemDecoration(MovieItemDecorator())
        binding.rwMoviesList.apply {
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.openDetailsScreenAction.observeEvent(viewLifecycleOwner) {
            AppNavigator.toDetailsScreen(this, it, binding.rwMoviesList)
        }

        viewModel.moviesLiveData.observe(viewLifecycleOwner) {
            adapter.setNewData(it)
        }
    }
}