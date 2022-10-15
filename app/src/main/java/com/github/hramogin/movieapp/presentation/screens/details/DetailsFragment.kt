package com.github.hramogin.movieapp.presentation.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.github.hramogin.movieapp.R
import com.github.hramogin.movieapp.databinding.FragmentDetailsBinding
import com.github.hramogin.movieapp.presentation.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class DetailsFragment : BaseFragment<DetailsViewModel, FragmentDetailsBinding>() {

    override val viewModel: DetailsViewModel by inject { parametersOf(param.filmId) }

    override fun getLayoutId(): Int = R.layout.fragment_details

    private val param: DetailsFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding =
        { inflater, container, attached ->
            FragmentDetailsBinding.inflate(inflater, container, attached)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.filmId.observe(viewLifecycleOwner) {
            binding.id.text = it
        }
    }
}