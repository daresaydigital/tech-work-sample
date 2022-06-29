//
//  CoordinatorTests.swift
//  DaresayChallengeTests
//
//  Created by Keihan Kamangar on 2022-06-29.
//

import XCTest
@testable import DaresayChallenge

class CoordinatorTest: XCTestCase {
    
    var window: UIWindow?
    var navigationController: UINavigationController!
    var appCoordinator: AppCoordinator?
    
    override func setUp() {
        super.setUp()
        
        window = UIWindow()
        navigationController = UINavigationController()
    }
    
    
    override func tearDown() {
        window = nil
        navigationController = nil
        appCoordinator = nil
        super.tearDown()
    }
    
    func testApplicationStarts() {
        
        appCoordinator = AppCoordinator(navigationController)
        
        window?.rootViewController = navigationController
        window?.makeKeyAndVisible()
        
        appCoordinator?.start(animated: true)
        
        XCTAssertEqual(window?.rootViewController, navigationController)
    }
}
