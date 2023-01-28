//
//  MockService.swift
//  DarsayTechTestTests
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import XCTest
import Combine
@testable import DarsayTechTest

final class MockService: MovieNetworkAPI, JSONLoader {
  
    func getPopularMovies() -> AnyPublisher<DarsayTechTest.ResultList<DarsayTechTest.Movie>, Error> {
        
        do {
            let publisher = Just(try loadJSON(filename: "most_popular_json", type: ResultList<Movie>.self))
            return publisher.setFailureType(to: Error.self).eraseToAnyPublisher()
        } catch {
            return Fail(outputType: ResultList<Movie>.self, failure: error).eraseToAnyPublisher()
        }
    }
    
    func getTopRatedMovies() -> AnyPublisher<DarsayTechTest.ResultList<DarsayTechTest.Movie>, Error> {
        do {
            let publisher = Just(try loadJSON(filename: "top_rated_json", type: ResultList<Movie>.self))
            return publisher.setFailureType(to: Error.self).eraseToAnyPublisher()
        } catch {
            return Fail(outputType: ResultList<Movie>.self, failure: error).eraseToAnyPublisher()
        }
    }
    
    func getMovieDetail(movieID: String) -> AnyPublisher<DarsayTechTest.Movie, Error> {
        do {
            let publisher = Just(try loadJSON(filename: "detail_json", type: Movie.self))
            return publisher.setFailureType(to: Error.self).eraseToAnyPublisher()
        } catch {
            return Fail(outputType: Movie.self, failure: error).eraseToAnyPublisher()
        }
    }
    
    func getMovieReviews(movieID: String) -> AnyPublisher<DarsayTechTest.ResultList<DarsayTechTest.Review>, Error> {
        do {
            let publisher = Just(try loadJSON(filename: "review_json", type: ResultList<Review>.self))
            return publisher.setFailureType(to: Error.self).eraseToAnyPublisher()
        } catch {
            return Fail(outputType: ResultList<Review>.self, failure: error).eraseToAnyPublisher()
        }
        
    }
}
