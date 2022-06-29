//
//  MoviesCoordinatorTest.swift
//  DaresayChallengeTests
//
//  Created by Keihan Kamangar on 2022-06-29.
//

import XCTest
@testable import DaresayChallenge

class MoviesCoordinatorTest: XCTestCase {
    
    var window: UIWindow?
    var navigationController: UINavigationController!
    var coordinator: MoviesCoordinator?
    
    override func setUp() {
        super.setUp()
        
        window = UIWindow()
        navigationController = UINavigationController()
        
        coordinator = MoviesCoordinator(navigationController)
        
        window?.rootViewController = navigationController
        window?.makeKeyAndVisible()
    }
    
    
    override func tearDown() {
        window = nil
        navigationController = nil
        coordinator = nil
        super.tearDown()
    }
    
    func testShowsMoviesListVC() {
        
        coordinator?.start(animated: true)
        
        let rootVC = navigationController.viewControllers[0] as? MoviesViewController
        
        XCTAssertNotNil(rootVC, "Check if root vc is MoviesViewController")
    }
    
    func testShowsMoviesDetailVC() {
        coordinator?.showMovieDetailViewController(with: MoviesModel())
        
        let visibleVC = navigationController.visibleViewController as? MovieDetailViewController
        XCTAssertNotNil(visibleVC, "Check if presented vc is MovieDetailViewController")
    }
    
    func testShowsFavoriteMoviesDetailVC() {
        coordinator?.showFavoriteMoviesViewController()
        
        let visibleVC = navigationController.visibleViewController as? FavoriteMoviesViewController
        XCTAssertNotNil(visibleVC, "Check if presented vc is FavoriteMoviesViewController")
    }
}
