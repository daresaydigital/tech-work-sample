package com.daresaydigital.presentation.feature.main.home.top_rated

import androidx.fragment.app.viewModels
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedMovieFragment : BaseFragment<TopRatedMovieViewModel>(){

    override val viewModel: TopRatedMovieViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_top_rated

}