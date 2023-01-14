//
//  TrendingViewModel.swift
//  MyMoviesTests
//
//  Created by Caio dos Santos Ambrosio on 1/14/23.
//

import XCTest
@testable import MyMovies

final class TrendingViewModelSpec: XCTestCase {

    private func getMockData(for path: String) -> Data? {
        guard let pathString = Bundle(for: type(of: self)).path(forResource: path, ofType: "json") else {
            fatalError("Mock json not found")
        }

        guard let jsonString = try? String(contentsOfFile: pathString, encoding: .utf8) else {
            fatalError("Mock json can not be converted to String")
        }

        return jsonString.data(using: .utf8)
    }

    private func getMockJson(for data: Data) -> TrendingResult {
        let jsonDecoder = JSONDecoder()
        jsonDecoder.keyDecodingStrategy = .convertFromSnakeCase
        do {
            let body = try jsonDecoder.decode(TrendingResult.self, from: data)
            return body
        } catch {
            fatalError("Error when decoding json")
        }
    }

    func testSuccessJson() {
        guard let data = getMockData(for: "trending-result") else {
            fatalError("Mock json can not be converted to Data")
        }

        let trending = getMockJson(for: data)
        let viewModel = TrendingViewModel(trending: trending)

        XCTAssertEqual(viewModel.title, trending.originalTitle)
        XCTAssertEqual(viewModel.posterImageURL, URL(string: "https://image.tmdb.org/t/p/w154/1NqwE6LP9IEdOZ57NCT51ftHtWT.jpg?api_key=16094d8ca19f9c0407db3d0b5203bd21"))
        XCTAssertEqual(viewModel.movieId, 315162)
    }

    func testNullableJson() {
        guard let data = getMockData(for: "trending-result-nullable") else {
            fatalError("Mock json can not be converted to Data")
        }

        let trending = getMockJson(for: data)
        let viewModel = TrendingViewModel(trending: trending)

        XCTAssertEqual(viewModel.title, "")
        XCTAssertEqual(viewModel.posterImageURL, URL(string: "https://image.tmdb.org/t/p/w154?api_key=16094d8ca19f9c0407db3d0b5203bd21"))
        XCTAssertEqual(viewModel.movieId, nil)
    }
}
