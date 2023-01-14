//
//  MainCoordinatorSpec.swift
//  MyMoviesTests
//
//  Created by Caio dos Santos Ambrosio on 1/14/23.
//

import XCTest
@testable import MyMovies

final class MainCoordinatorSpec: XCTestCase {
    private var mainCoordinator: MainCoordinatorMock!

    override func setUp() {
        super.setUp()

        let navigationController = UINavigationController(rootViewController: UIViewController())
        mainCoordinator = MainCoordinatorMock(navigationController: navigationController)
    }

    func testMainCoordinatorStart() {
        mainCoordinator.start()

        XCTAssertTrue(mainCoordinator.startCalled)
    }

    func testEventOccurred() {
        mainCoordinator.eventOccurred(with: .movieClicked, parameters: nil)

        XCTAssertTrue(mainCoordinator.eventOccurredCalled)
    }
}
