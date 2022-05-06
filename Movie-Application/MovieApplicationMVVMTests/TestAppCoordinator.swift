//
//  TestAppCoordinator.swift
//  MovieApplicationMVVMTests
//
//  Created by Mohanna Zakizadeh on 5/6/22.
//

import XCTest
@testable import MovieApplicationMVVM

class TestAppCoordinator: XCTestCase {

    var coordinator: AppCoordinator?
    var window: UIWindow?

    override func setUpWithError() throws {
        window = UIWindow(frame: UIScreen.main.bounds)
        coordinator = AppCoordinator(window: window)
    }

    override func tearDownWithError() throws {
        window = nil
        coordinator = nil
    }

    func testStart() throws {
        // given
        guard let coordinator = coordinator else {
            throw UnitTestError()
        }
        
        // when
        coordinator.start()
        
        // then
        XCTAssertNotNil(coordinator.window?.rootViewController)

    }
    
    func testChangeTabBarIndex() throws {
        // given
        let index  = 0
        
        // when
        coordinator?.changeTabBarIndex(to: 0)
        
        // then
        XCTAssertEqual(index, coordinator?.tabBarController?.selectedIndex)
    }

}
