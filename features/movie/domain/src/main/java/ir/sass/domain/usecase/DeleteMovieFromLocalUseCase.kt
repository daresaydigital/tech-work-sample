package ir.sass.domain.usecase

import ir.sass.base_domain.usecase.MotherUseCaseWithOnlyInput
import ir.sass.domain.model.ResultModel
import ir.sass.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteMovieFromLocalUseCase @Inject constructor(
    private val repository: MovieRepository
) : MotherUseCaseWithOnlyInput<ResultModel>() {
    override fun invoke(input: ResultModel) {
        repository.deleteFromLocal(input)
    }
}