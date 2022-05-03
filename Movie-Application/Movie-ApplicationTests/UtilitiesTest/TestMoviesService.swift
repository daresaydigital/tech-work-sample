//
//  TestMoviesService.swift
//  Movie-ApplicationTests
//
//  Created by Mohanna Zakizadeh on 5/3/22.
//

import XCTest
@testable import Movie_Application

class TestMoviesService: XCTestCase {

    var sut: MoviesService?
    var moviesJson: Data?
    var movieDetails: Data?

    override func setUpWithError() throws {
        let bundle = Bundle(for: type(of: self))
        if let path = bundle.path(forResource: "Movies", ofType: "json") {
            do {
                let data = try Data(contentsOf: URL(fileURLWithPath: path), options: .mappedIfSafe)
                self.moviesJson = data
            } catch {

            }
        }

        if let path = bundle.path(forResource: "Movie", ofType: "json") {
            do {
                let data = try Data(contentsOf: URL(fileURLWithPath: path), options: .mappedIfSafe)
                self.movieDetails = data
            } catch {

            }
        }

    }

    override func tearDownWithError() throws {
        moviesJson = nil
        sut = nil
    }

    func testGetPopularMovies() throws {

        // Given
        let urlSessionMock = URLSessionMock()
        urlSessionMock.data = moviesJson
        let mockRequestManager = RequestManagerMock(session: urlSessionMock, validator: MockResponseValidator())
        sut = MoviesService(requestManager: mockRequestManager)
        let expectation = XCTestExpectation(description: "Async popular movies test")
        var movies: Movies?

        // When
        sut?.getPopularMovies(page: 1, completionHandler: { (result) in
            defer {
                expectation.fulfill()
            }
            switch result {
            case .success(let popularMovies):
                movies = popularMovies
            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        })

        // Then
        wait(for: [expectation], timeout: 5)
        XCTAssertTrue(movies?.results.count == 20)
    }

    func testGetTopRatedMovies() throws {
        // Given
        let urlSessionMock = URLSessionMock()
        urlSessionMock.data = moviesJson
        let mockRequestManager = RequestManagerMock(session: urlSessionMock, validator: MockResponseValidator())
        sut = MoviesService(requestManager: mockRequestManager)
        let expectation = XCTestExpectation(description: "Async top rated movies test")
        var movies: Movies?

        // When
        sut?.getTopRatedMovies(page: 1, completionHandler: { (result) in
            defer {
                expectation.fulfill()
            }
            switch result {
            case .success(let topRatedMovies):
                movies = topRatedMovies
            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        })

        // Then
        wait(for: [expectation], timeout: 5)
        XCTAssertTrue(movies?.results.count == 20)
    }

    func testGetMoviedetails() throws {
        // Given
        let urlSessionMock = URLSessionMock()
        urlSessionMock.data = movieDetails
        let mockRequestManager = RequestManagerMock(session: urlSessionMock, validator: MockResponseValidator())
        sut = MoviesService(requestManager: mockRequestManager)
        let expectation = XCTestExpectation(description: "Async movie details test")
        var movie: MovieDetail?

        // When
        sut?.getMovieDetails(id: 550, completionHandler: { (result) in
            defer {
                expectation.fulfill()
            }
            switch result {
            case .success(let topRatedMovies):
                movie = topRatedMovies
            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        })

        // Then
        wait(for: [expectation], timeout: 5)
        XCTAssertTrue(movie?.id == 550)
    }

}
