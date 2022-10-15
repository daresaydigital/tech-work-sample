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
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListFragment : BaseFragment<MoviesListViewModel, FragmentMoviesListBinding>() {

    override val viewModel: MoviesListViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.fragment_movies_list

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMoviesListBinding =
        { inflater, container, attached ->
            FragmentMoviesListBinding.inflate(inflater, container, attached)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.openDetailsScreenAction.observeEvent(viewLifecycleOwner) {
            AppNavigator.toDetailsScreen(this, it)
        }
    }
}