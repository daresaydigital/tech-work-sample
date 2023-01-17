//
//  MovieViewModelSpec.swift
//  MyMoviesTests
//
//  Created by Caio dos Santos Ambrosio on 1/14/23.
//

import XCTest
@testable import MyMovies

final class MovieViewModelSpec: XCTestCase {
    var apiLoader: APILoader<MovieAPI>!
    var expectation: XCTestExpectation!
    let movieId: Int64 = 238
    let apiURL = URL(string: APIPath().fetchMovieDetails(for: 238))!

    override func setUp() {
        let configuration = URLSessionConfiguration.default
        configuration.protocolClasses = [MockURLProtocol.self]
        let urlSession = URLSession.init(configuration: configuration)

        apiLoader = APILoader(apiHandler: MovieAPI(), urlSession: urlSession)
        expectation = expectation(description: "Expectation")
    }

    func testSuccessCall() {
        let data = JsonHelper().getMockData(for: "movie-success")

        MockURLProtocol.requestHandler = { request in
            guard let url = request.url, url == self.apiURL else {
                fatalError("Trying to test a different URL")
            }

            let response = HTTPURLResponse(url: self.apiURL, statusCode: 200, httpVersion: nil, headerFields: nil)!
            return (response, data)
        }

        let viewModel = MovieViewModel(apiLoader: self.apiLoader, movieId: movieId)

        viewModel.fetchMovie { viewModel, error in
            XCTAssertNotNil(viewModel)
            XCTAssertEqual(viewModel?.title, "The Godfather")
            XCTAssertEqual(viewModel?.imageUrl, URL(string: "https://image.tmdb.org/t/p/w185/3bhkrj58Vtu7enYsRolD1fZdja1.jpg?api_key=16094d8ca19f9c0407db3d0b5203bd21"))
            XCTAssertEqual(viewModel?.genres, ["Drama", "Crime"])
            XCTAssertEqual(viewModel?.overview, "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.")
            XCTAssertEqual(viewModel?.rate, "8.71")
            XCTAssertEqual(viewModel?.totalVoteCount, "17270")
            XCTAssertEqual(viewModel?.releaseDate, "03/14/1972")
            self.expectation.fulfill()
        }
        wait(for: [expectation], timeout: 100.0)
    }

    func testErrorCall() {
        let data = JsonHelper().getMockData(for: "movie-error")

        MockURLProtocol.requestHandler = { request in
            guard let url = request.url, url == self.apiURL else {
                fatalError("Trying to test a different URL")
            }

            let response = HTTPURLResponse(url: self.apiURL, statusCode: 400, httpVersion: nil, headerFields: nil)!
            return (response, data)
        }

        let viewModel = MovieViewModel(apiLoader: self.apiLoader, movieId: movieId)

        viewModel.fetchMovie { viewModel, error in
            XCTAssertNotNil(error)
            XCTAssertEqual(error?.message, "Error when communicating with API")
            self.expectation.fulfill()
        }
        wait(for: [expectation], timeout: 100.0)
    }
}
