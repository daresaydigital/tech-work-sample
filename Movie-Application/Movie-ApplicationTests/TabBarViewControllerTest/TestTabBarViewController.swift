//
//  TestTabBarViewController.swift
//  Movie-ApplicationTests
//
//  Created by Mohanna Zakizadeh on 4/24/22.
//

import XCTest
@testable import Movie_Application

class TestTabBarViewController: XCTestCase {
    var view: TabBarViewContorller!

    override func setUpWithError() throws {
        view = TabBarViewContorller()
        view.loadView()
        view.viewDidLoad()
    }

    override func tearDownWithError() throws {
        view = nil
    }

    func testViewHasTopRatedIcon() throws {
        XCTAssert(type(of: view.topRatedIcon) == UIImage?.self)
    }
    
    func testViewHasFavoriteIcon() throws {
        XCTAssert(type(of: view.favoriteIcon) == UIImage?.self)
    }
    
    func testViewHasPopularIcon() throws {
        XCTAssert(type(of: view.popularIcon) == UIImage?.self)
    }
    
    func testTopRatedMovieViewControllerExists() throws {
        XCTAssertNotNil(view.topRatedMoviesViewController)
    }
    
    func testPopularMovieViewControllerExists() throws {
        XCTAssertNotNil(view.popularMoviesViewController)
    }
    
    func testFavoriteMovieViewControllerExists() throws {
        XCTAssertNotNil(view.favoriteMoviesViewController)
    }
    
    func testViewControllerHasSetupTopRatedMoviesViewControllerMethod() throws {
        _ = view.setupTopRatedMoviesViewController()
    }
    
    func testViewControllerHasSetupPopularMoviesViewControllerMethod() throws {
        _ = view.setupPopularMoviesViewController()
    }
    
    func testViewControllerHasSetupFavoritesMoviesViewControllerMethod() throws {
        _ = view.setupFavoriteMoviesViewController()
    }
    
    func testViewHasClearBackground() throws {
        XCTAssertEqual(view.view.backgroundColor, .clear)
    }
    
    func testTopRatedMoviesTabBarItemNotNil() throws {
        let tabBarItem = UITabBarItem(title: "Top Rated", image: UIImage(systemName: "list.number"), tag: 0)
        let vc = view.setupTopRatedMoviesViewController()
        XCTAssertEqual(tabBarItem.title, vc.tabBarItem.title)
        XCTAssertEqual(tabBarItem.image, vc.tabBarItem.image)
    }
    
    func testPopularMoviesTabBarItemNotNil() throws {
        let tabBarItem = UITabBarItem(title: "Popular", image: UIImage(systemName: "flame"), selectedImage: UIImage(systemName: "flame.fill"))
        let vc = view.setupPopularMoviesViewController()
        XCTAssertEqual(tabBarItem.title, vc.tabBarItem.title)
        XCTAssertEqual(tabBarItem.image, vc.tabBarItem.image)
        XCTAssertEqual(tabBarItem.selectedImage, vc.tabBarItem.selectedImage)
    }
    
    func testFavoriteMoviesTabBarItemNotNil() throws {
        let tabBarItem = UITabBarItem(title: "Favorite", image: UIImage(systemName: "star"), selectedImage: UIImage(systemName: "star.fill"))
        let vc = view.setupFavoriteMoviesViewController()
        XCTAssertEqual(tabBarItem.title, vc.tabBarItem.title)
        XCTAssertEqual(tabBarItem.image, vc.tabBarItem.image)
        XCTAssertEqual(tabBarItem.selectedImage, vc.tabBarItem.selectedImage)
    }
}

