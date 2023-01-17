//
//  TrendingTests.swift
//  MyMoviesTests
//
//  Created by Caio dos Santos Ambrosio on 1/14/23.
//

import XCTest
@testable import MyMovies

final class TrendingListViewModelSpec: XCTestCase {
    var apiLoader: APILoader<TrendingAPI>!
    var expectation: XCTestExpectation!
    let apiURL = URL(string: APIPath().fetchTrending(with: TrendingParams(mediaType: .movie, timeWindow: .day)))!

    override func setUp() {
        let configuration = URLSessionConfiguration.default
        configuration.protocolClasses = [MockURLProtocol.self]
        let urlSession = URLSession.init(configuration: configuration)

        apiLoader = APILoader(apiHandler: TrendingAPI(), urlSession: urlSession)
        expectation = expectation(description: "Expectation")
    }

    func testSuccessCall() {
        let data = JsonHelper().getMockData(for: "trending-success")

        MockURLProtocol.requestHandler = { request in
            guard let url = request.url, url == self.apiURL else {
                fatalError("Trying to test a different URL")
            }

            let response = HTTPURLResponse(url: self.apiURL, statusCode: 200, httpVersion: nil, headerFields: nil)!
            return (response, data)
        }

        let viewModel = TrendingListViewModel(apiLoader: self.apiLoader)

        viewModel.fetchTrendings(of: TrendingParams(mediaType: .movie, timeWindow: .day)) { viewModel, error in
            XCTAssertNotNil(viewModel)
            XCTAssertEqual(viewModel?.numberOfRowsInSection, 20)
            XCTAssertEqual(viewModel?.titlePage, "Trending")
            XCTAssertEqual(viewModel?.getTrending(2).title, "Avatar: The Way of Water")
            self.expectation.fulfill()
        }
        wait(for: [expectation], timeout: 100.0)
    }

    func testErrorCall() {
        let data = JsonHelper().getMockData(for: "trending-error")

        MockURLProtocol.requestHandler = { request in
            guard let url = request.url, url == self.apiURL else {
                fatalError("Trying to test a different URL")
            }

            let response = HTTPURLResponse(url: self.apiURL, statusCode: 400, httpVersion: nil, headerFields: nil)!
            return (response, data)
        }

        let viewModel = TrendingListViewModel(apiLoader: self.apiLoader)

        viewModel.fetchTrendings(of: TrendingParams(mediaType: .movie, timeWindow: .day)) { viewModel, error in
            XCTAssertNotNil(error)
            XCTAssertEqual(error?.message, "Error when communicating with API")
            self.expectation.fulfill()
        }
        wait(for: [expectation], timeout: 100.0)
    }
}
