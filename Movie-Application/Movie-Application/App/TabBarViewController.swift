//
//  TabBarViewController.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/24/22.
//

import UIKit

class TabBarViewContorller: UITabBarController {
    
    // MARK: - Properties
    var topRatedMoviesViewController: UIViewController!
    var popularMoviesViewController: UIViewController!
    var favoriteMoviesViewController: UIViewController!
    
    let topRatedIcon = UIImage(systemName: "list.number")
    let favoriteIcon = UIImage(systemName: "star")
    let popularIcon  = UIImage(systemName: "flame")
    
    
    // MARK: - Lifecycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        topRatedMoviesViewController = setupTopRatedMoviesViewController()
        popularMoviesViewController = setupPopularMoviesViewController()
        favoriteMoviesViewController = setupFavoriteMoviesViewController()
        
        self.viewControllers = [popularMoviesViewController, topRatedMoviesViewController, favoriteMoviesViewController]
        
        self.toolbarItems = []
        self.selectedIndex = 1
        
        self.applyTheme()
    }
    
    // MARK: - Theme
    
    func applyTheme() {
//        view.backgroundColor = .
    }
    
    // MARK: - Private functions
    func setupTopRatedMoviesViewController() -> UIViewController {
        let topRatedViewController = TopRatedMoviesModule().build()
        let tabBarItem = UITabBarItem(title: "Top Rated", image: topRatedIcon, tag: 0)
        topRatedViewController.tabBarItem = tabBarItem
        return topRatedViewController
    }
    
    func setupPopularMoviesViewController() -> UIViewController {
        let popularViewController = PopularMoviesModule().build()
        popularViewController.tabBarItem = UITabBarItem(title: "Popular", image: popularIcon, selectedImage: UIImage(systemName: "flame.fill"))
        return popularViewController
    }
    
    func setupFavoriteMoviesViewController() -> UIViewController {
        let favoriteViewController = WatchListMoviesModule().build()
        favoriteViewController.tabBarItem = UITabBarItem(title: "WatchList", image: favoriteIcon, selectedImage: UIImage(systemName: "star.fill"))
        return favoriteViewController
    }
    
    override func tabBar(_ tabBar: UITabBar, didSelect item: UITabBarItem) {
        NotificationCenter.default.post(name: TabBarViewContorller.tabBarDidTapNotification, object: nil)
    }
}

extension TabBarViewContorller {
    static var tabBarDidTapNotification: NSNotification.Name {
        NSNotification.Name(rawValue: "TabBarViewContorller.tabBarDidTapNotification")
    }
}
