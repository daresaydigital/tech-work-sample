//
//  TabBarView.swift
//  TabBar
//
//  Created by mohannazakizadeh on 4/23/22.
//

import UIKit

final class TabBarView: UITabBarController, ViewInterface {
	
	var presenter: TabBarPresenterViewInterface!
	
	// MARK: - Properties
    var topRatedMoviesViewController: UIViewController!
    var popularMoviesViewController: UIViewController!
    var favoriteMoviesViewController: UIViewController!
    
    let topRatedIcon = UIImage(systemName: "arrow.up")
    let favoriteIcon = UIImage(systemName: "suit.heart.fill")
    let popularIcon  = UIImage(systemName: "star.fill")
    
	// MARK: - Initialize
    
	// MARK: - Lifecycle
	
	override func viewDidLoad() {
		super.viewDidLoad()
        
		self.applyTheme()
	}
    
    override func viewWillAppear(_ animated: Bool) {
        topRatedMoviesViewController = setupTopRatedMoviesViewController()
        popularMoviesViewController = setupPopularMoviesViewController()
        favoriteMoviesViewController = setupFavoriteMoviesViewController()
        
        self.viewControllers = [topRatedMoviesViewController, popularMoviesViewController, favoriteMoviesViewController]
        
        self.toolbarItems = []
        self.selectedIndex = 1
    }
	
	// MARK: - Theme
	
	func applyTheme() {
        view.backgroundColor = .clear
        topRatedIcon?.withTintColor(.yellow)
	}
	
	// MARK: - Private functions
    private func setupTopRatedMoviesViewController() -> UIViewController {
        let topRatedViewController = TopRatedMoviesModule().build()
        let tabBarItem = UITabBarItem(title: "Top Rated", image: topRatedIcon, tag: 0)
        topRatedViewController.tabBarItem = tabBarItem
        return topRatedViewController
    }
    
    private func setupPopularMoviesViewController() -> UIViewController {
        let popularViewController = PopularMoviesModule().build()
        popularViewController.tabBarItem = UITabBarItem(title: "Popular", image: popularIcon, tag: 1)
        return popularViewController
    }
    
    private func setupFavoriteMoviesViewController() -> UIViewController {
        let favoriteViewController = FavoriteMoviesModule().build()
        favoriteViewController.tabBarItem = UITabBarItem(title: "Favorite", image: favoriteIcon, tag: 2)
        return favoriteViewController
    }
	// MARK: - Actions
	
	
}

extension TabBarView: TabBarViewInterface {
	
}
