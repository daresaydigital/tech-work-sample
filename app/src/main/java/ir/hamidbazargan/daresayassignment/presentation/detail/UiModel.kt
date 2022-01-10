package ir.hamidbazargan.daresayassignment.presentation.detail

import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity

sealed class UiModel {
    object Loading: UiModel()
    object Bookmark: UiModel()
    data class Success(val movie: MovieEntity): UiModel()
    data class Error(val errorMessage: String): UiModel()
}
