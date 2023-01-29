//
//  MovieNetworkAPI.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation
import Combine

protocol MovieNetworkAPI {
    func getPopularMovies() -> AnyPublisher<ResultList<Movie>, Error>
    func getTopRatedMovies() -> AnyPublisher<ResultList<Movie>, Error>
    func getMovieDetail(movieID: String) -> AnyPublisher<Movie, Error>
    func getMovieReviews(movieID: String) -> AnyPublisher<ResultList<Review>, Error>
}
