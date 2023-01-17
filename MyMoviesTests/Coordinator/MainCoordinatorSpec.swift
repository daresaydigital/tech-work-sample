//
//  MainCoordinatorSpec.swift
//  MyMoviesTests
//
//  Created by Caio dos Santos Ambrosio on 1/14/23.
//

import XCTest
@testable import MyMovies

final class MainCoordinatorSpec: XCTestCase {
    private var mainCoordinator: MainCoordinator!
    private var navigationController: MockUINavigationController!

    override func setUp() {
        super.setUp()

        navigationController = MockUINavigationController()
        mainCoordinator = MainCoordinator(navigationController: navigationController)
    }

    func testMainCoordinatorStart() {
        mainCoordinator.start()

        XCTAssertTrue(navigationController.setViewControllersCalled)
        XCTAssertTrue(navigationController.controllers[0] is HomeViewController)
    }

    func testEventMovieClicked() {
        let movieId: Int64 = 238
        mainCoordinator.eventOccurred(with: .movieClicked, parameters: movieId)

        XCTAssertTrue(navigationController.pushViewControllerCalled)
        XCTAssertTrue(navigationController.controllers[0] is MovieViewController)
    }

    func testEventFavoritesClicked() {
        mainCoordinator.eventOccurred(with: .favoriteClicked, parameters: nil)

        XCTAssertTrue(navigationController.pushViewControllerCalled)
        XCTAssertTrue(navigationController.controllers[0] is MovieListViewController)
    }
}
