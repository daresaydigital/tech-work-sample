package com.daresaydigital.presentation.feature.main.home

import androidx.fragment.app.viewModels
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>(){

    override val viewModel: HomeViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_home

}