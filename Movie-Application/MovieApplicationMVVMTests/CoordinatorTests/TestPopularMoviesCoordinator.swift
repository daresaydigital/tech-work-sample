//
//  TestPopularMoviesCoordinator.swift
//  MovieApplicationMVVMTests
//
//  Created by Mohanna Zakizadeh on 5/7/22.
//

import XCTest
@testable import MovieApplicationMVVM

final class TestPopularMoviesCoordinator: XCTestCase {

    var coordinator: PopularMoviesCoordinator?

    override func setUpWithError() throws {
        coordinator = PopularMoviesCoordinator(tabBarItem: UITabBarItem(),
                                                parentCoordinator: AppCoordinator(window: nil))

    }

    override func tearDownWithError() throws {
        coordinator = nil
    }

    func testCoordinatorHasNavigationController() throws {
        XCTAssertNotNil(coordinator?.navigationController)
    }

}
