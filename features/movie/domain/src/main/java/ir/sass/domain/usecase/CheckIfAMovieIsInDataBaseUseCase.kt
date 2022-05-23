package ir.sass.domain.usecase

import ir.sass.base_domain.model.Domain
import ir.sass.base_domain.usecase.MotherUseCase
import ir.sass.domain.model.ResultModel
import ir.sass.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIfAMovieIsInDataBaseUseCase @Inject constructor(
    private val repository: MovieRepository
) : MotherUseCase<ResultModel,Boolean>() {
    override fun invoke(input: ResultModel): Flow<Domain<Boolean>> = repository.isExistOnDb(input)
}