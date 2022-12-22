//
//  MovieGridViewModelTests.swift
//  UnitTests
//
//  Created by Richard Segerblom on 2022-12-21.
//

import XCTest
import Combine

@testable import Movies

final class MovieGridViewModelTests: XCTestCase {
    private var subscriptions: [AnyCancellable] = []
    
    func test_toggleOnMostPopularMovies_expectMoviesToBeMostPopular() {
        let didReceiveResponse = expectation(description: #function)
        let sut = makeSUT()
        
        sut.$movies
            .drop { $0.isEmpty }
            .sink { movies in
                XCTAssertEqual(movies.count, 2)
                didReceiveResponse.fulfill()
            }
            .store(in: &subscriptions)
        
        sut.toggle(0)
        
        wait(for: [didReceiveResponse], timeout: 0.2)
    }
    
    func test_toggleOnTopRatedMovies_expectMoviesToBeTopRated() {
        let didReceiveResponse = expectation(description: #function)
        let sut = makeSUT()
        
        sut.$movies
            .drop { $0.isEmpty }
            .dropFirst()
            .sink { movies in
                XCTAssertEqual(movies.count, 1)
                didReceiveResponse.fulfill()
            }
            .store(in: &subscriptions)
        
        sut.toggle(1)
        
        wait(for: [didReceiveResponse], timeout: 0.2)
    }
    
    private func makeSUT() -> MovieGrid.ViewModel {
        let mostPopularMovies: [Movie] = [
            .init(id: 1, title: "", posterURL: "", releaseDate: "", description: ""),
            .init(id: 2, title: "", posterURL: "", releaseDate: "", description: ""),
        ]
        let topRatedMovies: [Movie] = [
            .init(id: 3, title: "", posterURL: "", releaseDate: "", description: ""),
        ]
        let service = MockMovieService(mostPopularMoviesResult: mostPopularMovies, topRatedMoviesResult: topRatedMovies)
        
        return MovieGrid.ViewModel(movieService: service)
    }
}
