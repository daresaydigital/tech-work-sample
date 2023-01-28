//
//  MovieNetworkAPIManager.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation
import Combine

class MovieNetworkAPIManager: MovieNetworkAPI {

    var executer: HTTPRequestProtocol
    
    init(executer: HTTPRequestProtocol) {
        self.executer = executer
    }
    
    static let shared = MovieNetworkAPIManager(executer: HTTPRequestExecuter())
    
    func getPopularMovies() -> AnyPublisher<ResultList<Movie>, Error> {
        return executer.performRequest(endpoint: MovieServiceEndPoint.getMostPopular, responseModel: ResultList<Movie>.self)
    }
    
    func getTopRatedMovies() -> AnyPublisher<ResultList<Movie>, Error> {
        return executer.performRequest(endpoint: MovieServiceEndPoint.getTopRated, responseModel: ResultList<Movie>.self)
    }
    
    func getMovieDetail(movieID: String) -> AnyPublisher<Movie, Error> {
        return executer.performRequest(endpoint: MovieServiceEndPoint.getDetail(movieID), responseModel: Movie.self)
    }
    
    func getMovieReviews(movieID: String) -> AnyPublisher<ResultList<Review>, Error> {
        return executer.performRequest(endpoint: MovieServiceEndPoint.getReviews(movieID), responseModel: ResultList<Review>.self)
    }
}
