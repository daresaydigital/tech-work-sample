//
//  TopRatedListViewModelSpec.swift
//  MyMoviesTests
//
//  Created by Caio dos Santos Ambrosio on 1/14/23.
//

import XCTest
@testable import MyMovies

final class TopRatedListViewModelSpec: XCTestCase {
    var apiLoader: APILoader<TopRatedAPI>!
    var expectation: XCTestExpectation!
    let apiURL = URL(string: APIPath().fetchTopRated())!

    override func setUp() {
        let configuration = URLSessionConfiguration.default
        configuration.protocolClasses = [MockURLProtocol.self]
        let urlSession = URLSession.init(configuration: configuration)

        apiLoader = APILoader(apiHandler: TopRatedAPI(), urlSession: urlSession)
        expectation = expectation(description: "Expectation")
    }

    func testSuccessCall() {
        let data = JsonHelper().getMockData(for: "top-rated-success")

        MockURLProtocol.requestHandler = { request in
            guard let url = request.url, url == self.apiURL else {
                fatalError("Trying to test a different URL")
            }

            let response = HTTPURLResponse(url: self.apiURL, statusCode: 200, httpVersion: nil, headerFields: nil)!
            return (response, data)
        }

        let viewModel = TopRatedListViewModel(apiLoader: self.apiLoader)

        viewModel.fetchTopRated { viewModel, error in
            XCTAssertNotNil(viewModel)
            XCTAssertEqual(viewModel?.numberOfRowsInSection, 20)
            XCTAssertEqual(viewModel?.titlePage, "Top Rated")
            XCTAssertEqual(viewModel?.getTrending(2).title, "The Godfather Part II")
            self.expectation.fulfill()
        }
        wait(for: [expectation], timeout: 100.0)
    }

    func testErrorCall() {
        let data = JsonHelper().getMockData(for: "top-rated-error")

        MockURLProtocol.requestHandler = { request in
            guard let url = request.url, url == self.apiURL else {
                fatalError("Trying to test a different URL")
            }

            let response = HTTPURLResponse(url: self.apiURL, statusCode: 400, httpVersion: nil, headerFields: nil)!
            return (response, data)
        }

        let viewModel = TopRatedListViewModel(apiLoader: self.apiLoader)

        viewModel.fetchTopRated { viewModel, error in
            XCTAssertNotNil(error)
            XCTAssertEqual(error?.message, "Error when communicating with API")
            self.expectation.fulfill()
        }
        wait(for: [expectation], timeout: 100.0)
    }
}
