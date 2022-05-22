package ir.sass.domain.usecase

import ir.sass.base_domain.usecase.MotherUseCaseWithOnlyInput
import ir.sass.base_domain.usecase.MotherUseCaseWithOnlyOutput
import ir.sass.basedomain.model.Domain
import ir.sass.domain.model.DiscoverMovieModel
import ir.sass.domain.model.ResultModel
import ir.sass.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteMovieFromLocalUseCase @Inject constructor(
    private val repository: MovieRepository
) : MotherUseCaseWithOnlyInput<ResultModel>() {
    override fun invoke(input: ResultModel) {
        repository.deleteFromLocal(input)
    }
}