package com.github.hramogin.movieapp.presentation.screens.details

import com.github.hramogin.movieapp.R
import com.github.hramogin.movieapp.domain.model.MovieDetails
import com.github.hramogin.movieapp.presentation.base.ResourceProvider
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor(private val resourceProvider: ResourceProvider) {

    fun map(details: MovieDetails): List<BaseDetailItem> {
        val items: MutableList<BaseDetailItem> = mutableListOf()

        if (details.description.isNotEmpty()) {
            items.add(TitleItem(title = resourceProvider.getString(R.string.movie_details_description_title)))
            items.add(DescriptionItem(description = details.description))
        }

        if (details.reviews.isNotEmpty()) {
            items.add(TitleItem(title = resourceProvider.getString(R.string.movie_details_reviews_title)))
            items.addAll(details.reviews.map { ReviewItem(review = it) })
        }

        return items
    }
}