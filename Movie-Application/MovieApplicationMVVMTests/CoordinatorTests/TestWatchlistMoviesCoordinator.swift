//
//  TestWatchlistMoviesCoordinator.swift
//  MovieApplicationMVVMTests
//
//  Created by Mohanna Zakizadeh on 5/7/22.
//

import XCTest
@testable import MovieApplicationMVVM

class TestWatchlistMoviesCoordinator: XCTestCase {
    var coordinator: WatchlistMoviesCoordinator?

    override func setUpWithError() throws {
        coordinator = WatchlistMoviesCoordinator(tabBarItem: UITabBarItem(),
                                                parentCoordinator: AppCoordinator(window: nil))

    }

    override func tearDownWithError() throws {
        coordinator = nil
    }

    func testCoordinatorHasNavigationController() throws {
        XCTAssertNotNil(coordinator?.navigationController)
    }

}
