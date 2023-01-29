//
//  MockExecuter.swift
//  DarsayTechTestTests
//
//  Created by Farzaneh on 11/8/1401 AP.
//swiftlint:disable force_cast

import Foundation
@testable import DarsayTechTest
import Combine

final class MockAllFailureHTTPRequestExecuter: HTTPRequestProtocol {
    
    func performRequest<T: Codable>(endpoint: EndPointTarget, responseModel: T.Type, cachedResponseOnError: Bool) -> AnyPublisher<T, Error> {
        Fail(outputType: T.self, failure: NetworkError.generalError).eraseToAnyPublisher()
    }
}

final class MockHTTPRequestExecuter: HTTPRequestProtocol {
    func performRequest<T: Codable>(endpoint: EndPointTarget, responseModel: T.Type, cachedResponseOnError: Bool) -> AnyPublisher<T, Error> {
        
        guard let endpoint = endpoint as? MovieServiceEndPoint else {
            return Fail(outputType: T.self, failure: NetworkError.badURL).eraseToAnyPublisher()
        }
        
        let mockService = MockService()
        
        switch endpoint {
            
        case .getTopRated where T.self == ResultList<Movie>.self:
            return mockService.getTopRatedMovies() as! AnyPublisher<T,Error>
        case .getReviews(let id) where T.self == ResultList<Review>.self:
           return mockService.getMovieReviews(movieID: id) as! AnyPublisher<T,Error>
        case .getDetail(let id) where T.self == Movie.self:
           return mockService.getMovieDetail(movieID: id) as! AnyPublisher<T,Error>
        case .getMostPopular where T.self == ResultList<Movie>.self:
           return mockService.getPopularMovies() as! AnyPublisher<T,Error>
            
        default:
            return Fail(outputType: T.self, failure: NetworkError.serverError).eraseToAnyPublisher()
        }
    }
}
