//
//  TrendingViewModel.swift
//  MyMoviesTests
//
//  Created by Caio dos Santos Ambrosio on 1/14/23.
//

import XCTest
@testable import MyMovies

final class TrendingViewModelSpec: XCTestCase {
    func testSuccessJson() {
        guard let data = JsonHelper().getMockData(for: "trending-result") else {
            fatalError("Mock json can not be converted to Data")
        }

        let trending: TrendingResult = JsonHelper().getMockJson(for: data)
        let viewModel = TrendingViewModel(trending: trending)

        XCTAssertEqual(viewModel.title, trending.originalTitle)
        XCTAssertEqual(viewModel.posterImageURL, URL(string: "https://image.tmdb.org/t/p/w154/1NqwE6LP9IEdOZ57NCT51ftHtWT.jpg?api_key=16094d8ca19f9c0407db3d0b5203bd21"))
        XCTAssertEqual(viewModel.movieId, 315162)
    }

    func testNullableJson() {
        guard let data = JsonHelper().getMockData(for: "trending-result-nullable") else {
            fatalError("Mock json can not be converted to Data")
        }

        let trending: TrendingResult = JsonHelper().getMockJson(for: data)
        let viewModel = TrendingViewModel(trending: trending)

        XCTAssertEqual(viewModel.title, "")
        XCTAssertEqual(viewModel.posterImageURL, URL(string: "https://image.tmdb.org/t/p/w154?api_key=16094d8ca19f9c0407db3d0b5203bd21"))
        XCTAssertEqual(viewModel.movieId, nil)
    }
}
