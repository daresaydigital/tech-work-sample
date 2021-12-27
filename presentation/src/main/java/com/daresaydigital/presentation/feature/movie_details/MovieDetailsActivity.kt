package com.daresaydigital.presentation.feature.movie_details

import androidx.activity.viewModels
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : BaseActivity<MovieDetailsViewModel>(){

    override val viewModel: MovieDetailsViewModel by viewModels()

    override fun layoutId(): Int = R.layout.activity_movie_details

}