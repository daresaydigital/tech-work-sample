package com.arashafsharpour.daresaymovie.features.movies.infrastracture

import com.arashafsharpour.daresaymovie.infrastructure.coordinator.Coordinator
import javax.inject.Inject

class MoviesCoordinator @Inject constructor() : Coordinator(), IMoviesCoordinator
