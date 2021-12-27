package com.daresaydigital.presentation.feature.main.favourite

import androidx.fragment.app.viewModels
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteMovieFragment : BaseFragment<FavouriteMovieViewModel>(){

    override val viewModel: FavouriteMovieViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_favourite_movie

}