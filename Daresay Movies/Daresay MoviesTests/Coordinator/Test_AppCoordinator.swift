//
//  Test_AppCoordinator.swift
//  Daresay MoviesTests
//
//  Created by Emad Bayramy on 12/15/21.
//

import XCTest
@testable import DaMovies_Dev

final class AppCoordinatorTests: XCTestCase {

    var sut: AppCoordinator?
    var window: UIWindow?
    
    override func tearDownWithError() throws {
        sut = nil
        window = nil
        try? super.tearDownWithError()
    }
    
    override func setUp() {
        let nav = UINavigationController()
        window = UIWindow(frame: UIScreen.main.bounds)
        sut = AppCoordinator(navigationController: nav, window: window)
    }
    
    override func tearDown() {
        sut = nil
        window = nil
    }
    
    func test_start() throws {
        // given
        guard let sut = sut else {
            throw UnitTestError()
        }
        
        // when
        sut.start(animated: false)
        
        // then
        XCTAssertEqual(sut.navigationController.viewControllers.count, 1)
        let rootVC = sut.navigationController.viewControllers[0] as? FlowControlViewController
        XCTAssertNotNil(rootVC, "Check if root vsc is FlowControlViewController")
    }
    
    func test_ToHome() throws {
        // given
        guard let sut = sut else {
            throw UnitTestError()
        }
        // when
        sut.toHome()
        
        // then
        XCTAssertTrue(sut.childCoordinators.count == 1)
        let visibleVC = sut.navigationController.visibleViewController as? HomeViewController
        XCTAssertNotNil(visibleVC, "Check if presented vc is HomeViewController")
    }
    
    func test_ChildDidFinish() throws {
        // given
        guard let sut = sut else {
            throw UnitTestError()
        }
        // when
        let child = HomeCoordinator(navigationController: sut.navigationController)
        sut.childCoordinators.append(child)
        sut.childDidFinish(child)
        
        // then
        XCTAssertTrue(sut.childCoordinators.count == 0)
    }
}
