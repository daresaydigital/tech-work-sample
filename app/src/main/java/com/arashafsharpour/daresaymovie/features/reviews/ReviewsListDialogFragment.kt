package com.arashafsharpour.daresaymovie.features.reviews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.databinding.BottomSheetReviewsBinding
import com.arashafsharpour.daresaymovie.features.reviews.adapter.ReviewAdapter
import com.arashafsharpour.daresaymovie.infrastructure.extensions.EndlessRecyclerViewScrollListener
import com.arashafsharpour.daresaymovie.infrastructure.extensions.launch
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReviewsListDialogFragment(private val movieName: String, private val movieId: String) :
    BaseBottomSheetDialogFragment<ReviewsListDialogViewModel, BottomSheetReviewsBinding>() {

    @Inject lateinit var reviewAdapter: ReviewAdapter
    private lateinit var reviewEndlessRecyclerViewListener: EndlessRecyclerViewScrollListener
    override val layoutResourceId: Int = R.layout.bottom_sheet_reviews
    override val viewModel: ReviewsListDialogViewModel by viewModels()

    override fun initializeBindingVariables() {
        binding.review = viewModel

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            DialogFragment.STYLE_NORMAL,
            R.style.BottomSheetDialogStyle
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movieName.postValue(movieName)
        setupReviewRecycler()
        setItemClickListeners()
        configRefreshLayout()
        viewModel.getReviews(movieId)
    }

    private fun setupReviewRecycler() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        reviewEndlessRecyclerViewListener =
            object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int) {
                    launch { viewModel.getReviews(movieId, page) }
                }
            }
        setupReviewLayoutManager(layoutManager)
    }

    private fun setupReviewLayoutManager(layoutManager: LinearLayoutManager) {
        binding.reviewRecyclerView.apply {
            this.layoutManager = layoutManager
            adapter = this@ReviewsListDialogFragment.reviewAdapter
            addOnScrollListener(reviewEndlessRecyclerViewListener)
        }
    }

    private fun configRefreshLayout() {
        binding.swipeLayout.setOnRefreshListener {
            binding.swipeLayout.isRefreshing = false
            reviewEndlessRecyclerViewListener.resetState()
            viewModel.review.value?.clear()
            viewModel.getReviews(movieId)
        }
    }

    private fun setItemClickListeners() {
        binding.back.setOnClickListener {
            dismiss()
        }
    }
}