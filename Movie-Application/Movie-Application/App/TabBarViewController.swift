//
//  TabBarViewController.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/24/22.
//

import UIKit

final class TabBarViewContorller: UITabBarController {
    
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
        
        self.applyTheme()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        topRatedMoviesViewController = setupTopRatedMoviesViewController()
        popularMoviesViewController = setupPopularMoviesViewController()
        favoriteMoviesViewController = setupFavoriteMoviesViewController()
        
        self.viewControllers = [popularMoviesViewController, topRatedMoviesViewController, favoriteMoviesViewController]
        
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
        popularViewController.tabBarItem = UITabBarItem(title: "Popular", image: popularIcon, selectedImage: UIImage(systemName: "flame.fill"))
        return popularViewController
    }
    
    private func setupFavoriteMoviesViewController() -> UIViewController {
        let favoriteViewController = FavoriteMoviesModule().build()
        favoriteViewController.tabBarItem = UITabBarItem(title: "Favorite", image: favoriteIcon, selectedImage: UIImage(systemName: "star.fill"))
        return favoriteViewController
    }
}
