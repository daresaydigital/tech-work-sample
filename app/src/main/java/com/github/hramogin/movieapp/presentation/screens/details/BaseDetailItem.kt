package com.github.hramogin.movieapp.presentation.screens.details

import com.github.hramogin.movieapp.domain.model.Review

abstract class BaseDetailItem(
    open val viewType: Int
)

class TitleItem(
    override val viewType: Int = 0,
    val title: String,
) : BaseDetailItem(viewType)

class DescriptionItem(
    override val viewType: Int = 1,
    val description: String
) : BaseDetailItem(viewType)

class ReviewItem(
    override val viewType: Int = 2,
    val review: Review,
) : BaseDetailItem(viewType)