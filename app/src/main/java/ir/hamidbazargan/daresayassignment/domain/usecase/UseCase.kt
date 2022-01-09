package ir.hamidbazargan.daresayassignment.domain.usecase

interface UseCase<P> {
    suspend fun execute(page: P)
}