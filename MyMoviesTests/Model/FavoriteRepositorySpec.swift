//
//  FavoriteRepositorySpec.swift
//  MyMoviesTests
//
//  Created by Caio dos Santos Ambrosio on 1/16/23.
//

import XCTest
@testable import MyMovies

final class FavoriteRepositorySpec: XCTestCase {
    var favoritesRepository: FavoriteRepository!
    var userDefaults: MockUserDefaults!

    override func setUp() {
        userDefaults = MockUserDefaults()
        favoritesRepository = FavoriteRepository(userDefaults: userDefaults)
    }

    func testInsertOrUpdateMovie() {
        guard let data = JsonHelper().getMockData(for: "favorite") else {
            fatalError("Mock json can not be converted to Data")
        }

        let trendings: [TrendingResult] = JsonHelper().getMockJson(for: data)

        trendings.forEach {
            let viewModel = TrendingViewModel(trending: $0)
            favoritesRepository.insetOrUpdateMovie(for: $0.id!, viewModel)
        }

        XCTAssertEqual(userDefaults.persisted.count, 21)
        XCTAssertEqual(favoritesRepository.getAllFavorites().count, 20)

        let movieId: Int64 = 1
        let trending = TrendingResult(posterPath: "", id: movieId, originalTitle: "", name: "")
        let viewModel = TrendingViewModel(trending: trending)

        favoritesRepository.insetOrUpdateMovie(for: movieId, viewModel)

        XCTAssertEqual(userDefaults.persisted.count, 22)
        XCTAssertEqual(favoritesRepository.getAllFavorites().count, 21)
    }

    func testDeleteMovie() {
        let movieId: Int64 = 1
        let trending = TrendingResult(posterPath: "", id: movieId, originalTitle: "", name: "")
        let viewModel = TrendingViewModel(trending: trending)

        favoritesRepository.insetOrUpdateMovie(for: movieId, viewModel)
        XCTAssertEqual(userDefaults.persisted.count, 2)
        XCTAssertEqual(favoritesRepository.getAllFavorites().count, 1)

        favoritesRepository.deleteMovie(for: movieId)
        XCTAssertEqual(userDefaults.persisted.count, 0)
        XCTAssertEqual(favoritesRepository.getAllFavorites().count, 0)
    }
}
