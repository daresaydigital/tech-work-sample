//
//  MockMovieService.swift
//  Movies
//
//  Created by Richard Segerblom on 2022-12-21.
//

import Combine

struct MockMovieService: MovieServicing {
    var mostPopularMoviesResult: [Movie] = []
    var topRatedMoviesResult: [Movie] = []
    
    func fetchMostPopular() -> AnyPublisher<[Movie], Error> {
        Just(mostPopularMoviesResult)
            .setFailureType(to: Error.self)
            .eraseToAnyPublisher()
    }
    
    func fetchTopRated() -> AnyPublisher<[Movie], Error> {
        Just(topRatedMoviesResult)
            .setFailureType(to: Error.self)
            .eraseToAnyPublisher()
    }
}
