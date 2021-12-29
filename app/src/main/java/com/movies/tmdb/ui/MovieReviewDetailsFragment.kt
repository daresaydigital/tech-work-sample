package com.movies.tmdb.ui

import androidx.navigation.fragment.navArgs
import com.movies.tmdb.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_review.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieReviewDetailsFragment @Inject constructor(

) : ParentFragment(R.layout.fragment_review_details) {

    override fun startViewActions() {

        setViews()


    }

    private fun setViews() {
        val args: MovieReviewDetailsFragmentArgs by navArgs()
        author.text = resources.getString(R.string.author, args.selectedReview.author)

        reviewContent.text = args.selectedReview.content
    }

}
