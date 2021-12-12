//
//  MovieDetailsAPIServiceFake.swift
//  TheMovieDBTests
//
//  Created by Ali Sani on 12/11/21.
//

import Foundation
@testable import TheMovieDB

final class MovieDetailsAPIServiceFake: MovieDetailsAPIService {
    func getMovieDetails(for movieId: Int, completed: @escaping (Result<MovieDetails, TMSError>) -> Void) {
        completed(.success(FakeModels.FakeMovieDetails))
    }
}
