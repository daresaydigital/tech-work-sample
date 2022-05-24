package ir.sass.base_domain.usecase

import ir.sass.base_domain.model.Domain
import kotlinx.coroutines.flow.Flow

/*we have 3 different type of use-case sometimes we have an action which has an input
but it won't return any value and sometimes it has output without any input
sometimes it has both  and sometimes it has not neither of them*/

abstract class MotherUseCase<in Input, Output> {
    abstract operator fun invoke(input: Input): Flow<Domain<Output>>
}

abstract class MotherUseCaseWithOnlyInput<in Input>() {
    abstract operator fun invoke(input: Input)
}

abstract class MotherUseCaseWithOnlyOutput<Output> {
    abstract operator fun invoke(): Flow<Domain<Output>>
}

abstract class MotherUseCaseEmpty {
    abstract operator fun invoke()
}



