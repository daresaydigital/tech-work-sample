package com.daresaydigital.presentation.feature.main.home.popular_movie

import androidx.fragment.app.viewModels
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMovieFragment : BaseFragment<PopularMovieViewModel>(){

    override val viewModel: PopularMovieViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_popular_movie

}