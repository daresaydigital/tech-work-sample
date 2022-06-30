//
//  MoviesViewModelTest.swift
//  DaresayChallengeTests
//
//  Created by Keihan Kamangar on 2022-06-30.
//

import XCTest
@testable import DaresayChallenge

class MoviesViewModelTest: XCTestCase {

    var sut: MoviesViewModel?
    var movies: [MoviesModel]?
    var getMoviesExpectation: XCTestExpectation!
    var populateExpectation: XCTestExpectation!
    
    override func setUp() {
        super.setUp()
    }

    override func tearDown() {
        sut = nil
        movies = nil
        getMoviesExpectation = nil
        populateExpectation = nil
        super.tearDown()
    }
    
    func testGetMovies() {
        sut = MoviesViewModel(moviesService: MoviesService.shared)
        sut?.delegate = self
        
        getMoviesExpectation = XCTestExpectation(description: "Async movies test")
        
        sut?.getPopularMovies()
        
        wait(for: [getMoviesExpectation], timeout: 5)
        XCTAssertNotNil(self.movies)
    }
    
    func testPopulate() {
        sut = MoviesViewModel(moviesService: MoviesService.shared)
        sut?.delegate = self

        populateExpectation = XCTestExpectation(description: "Async populate test")

        sut?.populate()

        wait(for: [populateExpectation], timeout: 5)
        XCTAssertNotNil(self.movies)
    }
}

// MARK: - MoviesViewModel Delegate
extension MoviesViewModelTest: MoviesViewModelDelegate {
    func populate(displayState: DisplayState<[MoviesModel]>) {
        switch displayState {
        case .loading:
            break
        case .success(let movies):
            self.movies = movies
            populateExpectation.fulfill()
        case .failure(let error):
            XCTFail(error)
        }
    }
    
    func displayMovies(displayState: DisplayState<[MoviesModel]>) {
        switch displayState {
        case .loading:
            break
        case .success(let movies):
            self.movies = movies
            getMoviesExpectation.fulfill()
        case .failure(let error):
            XCTFail(error)
        }
    }
}
