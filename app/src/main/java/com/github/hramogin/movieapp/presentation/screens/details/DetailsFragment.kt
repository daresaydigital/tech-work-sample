package com.github.hramogin.movieapp.presentation.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.github.hramogin.movieapp.BuildConfig
import com.github.hramogin.movieapp.R
import com.github.hramogin.movieapp.data.network.GlideApp
import com.github.hramogin.movieapp.databinding.FragmentDetailsBinding
import com.github.hramogin.movieapp.navigation.AppNavigator.onHandleBack
import com.github.hramogin.movieapp.presentation.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class DetailsFragment : BaseFragment<DetailsViewModel, FragmentDetailsBinding>() {

    override val viewModel: DetailsViewModel by inject { parametersOf(param.filmId) }

    override fun getLayoutId(): Int = R.layout.fragment_details

    private val param: DetailsFragmentArgs by navArgs()

    private lateinit var adapter: DetailsAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding =
        { inflater, container, attached ->
            FragmentDetailsBinding.inflate(inflater, container, attached)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivMovieImage.transitionName = param.filmId
        binding.toolbar.setNavigationOnClickListener {
            onHandleBack(this)
        }
        initRecycler()

        viewModel.movieDetails.observe(viewLifecycleOwner) {
            binding.collapsingToolbarLayout.title = it.title
            GlideApp.with(requireContext())
                .load(BuildConfig.BASE_IMAGE_URL + it.posterPath)
                .dontTransform()
                .into(binding.ivMovieImage)
        }
        viewModel.detailsItem.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }
    }

    private fun initRecycler() {
        adapter = DetailsAdapter()
        binding.rvMovieDetailsContent.adapter = adapter
    }
}