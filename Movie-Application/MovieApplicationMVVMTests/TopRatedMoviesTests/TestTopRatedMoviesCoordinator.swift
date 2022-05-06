//
//  TestTopRatedMoviesCoordinator.swift
//  MovieApplicationMVVMTests
//
//  Created by Mohanna Zakizadeh on 5/6/22.
//

import XCTest
@testable import MovieApplicationMVVM

class TestTopRatedMoviesCoordinator: XCTestCase {
    var coordinator: TopRatedMoviesCoordinator?

    override func setUpWithError() throws {
        coordinator = TopRatedMoviesCoordinator(tabBarItem: UITabBarItem(), parentCoordinator: AppCoordinator(window: nil))
        
    }

    override func tearDownWithError() throws {
        coordinator = nil
    }
    
    func testChangeTabBarIndexFunctionExists() throws {
        coordinator?.changeTabBarIndex(to: 0)
    }

}
