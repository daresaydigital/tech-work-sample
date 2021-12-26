package com.arashafsharpour.daresaymovie.features.moviedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.databinding.FragmentMovieDetailBinding
import com.arashafsharpour.daresaymovie.features.moviedetail.adapter.CastAdapter
import com.arashafsharpour.daresaymovie.features.moviedetail.adapter.GenreAdapter
import com.arashafsharpour.daresaymovie.features.moviedetail.adapter.PostersAdapter
import com.arashafsharpour.daresaymovie.features.reviews.ReviewsListDialogFragment
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseFragment
import com.arashafsharpour.daresaymovie.persistence.MOVIE_REVIEW_DIALOG_TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<MovieDetailViewModel, FragmentMovieDetailBinding>() {

    @Inject
    lateinit var genreAdapter: GenreAdapter
    @Inject
    lateinit var castAdapter: CastAdapter
    @Inject
    lateinit var postersAdapter: PostersAdapter
    override val layoutResourceId: Int = R.layout.fragment_movie_detail
    override val viewModel: MovieDetailViewModel by activityViewModels()

    override fun initializeBindingVariables() {
        binding.movie = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("id")?.let {
            getMovieDetail(it)
            getCredits(it)
            getReviews(it)
            getPosters(it)
        }
    }

    private fun configBindingClickListeners() {
        binding.readMoreText.setOnClickListener {
            viewModel.movieDetail.value?.let {
                if (parentFragmentManager.findFragmentByTag(MOVIE_REVIEW_DIALOG_TAG) == null)
                    ReviewsListDialogFragment(
                        it.title,
                        it.id.toString()
                    ).show(parentFragmentManager, MOVIE_REVIEW_DIALOG_TAG)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtonActions()
        setupGenreRecyclerView()
        setupCastRecyclerView()
        setupPostersAdapter()
        configBindingClickListeners()
    }

    private fun setupCastRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.castRecyclerView.apply {
            this.layoutManager = layoutManager
            adapter = this@MovieDetailFragment.castAdapter
        }
    }

    private fun setupGenreRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.genreRecyclerView.apply {
            this.layoutManager = layoutManager
            adapter = this@MovieDetailFragment.genreAdapter
        }
    }

    private fun setupPostersAdapter() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.posterRecyclerView.apply {
            this.layoutManager = layoutManager
            adapter = this@MovieDetailFragment.postersAdapter
        }
    }

    private fun setupButtonActions() {
        binding.back.setOnClickListener { navController.popBackStack() }
    }


    private fun getMovieDetail(id: String) {
        viewModel.getMovieDetail(id)
    }

    private fun getCredits(id: String) {
        viewModel.getCredits(id)
    }

    private fun getReviews(id: String) {
        viewModel.getReviews(id)
    }

    private fun getPosters(id: String) {
        viewModel.getPosters(id)
    }

}