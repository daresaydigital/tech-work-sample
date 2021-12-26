package com.arashafsharpour.daresaymovie.infrastructure.di

import com.arashafsharpour.daresaymovie.features.reviews.infrastracture.IReviewsListCoordinator
import com.arashafsharpour.daresaymovie.features.reviews.infrastracture.ReviewsListCoordinator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.FlowPreview

@FlowPreview
@InstallIn(SingletonComponent::class)
@Module
interface ReviewsModuleBinder {
    @Binds
    fun bindMainCoordinator(
        reviewsCoordinator: ReviewsListCoordinator
    ): IReviewsListCoordinator
}