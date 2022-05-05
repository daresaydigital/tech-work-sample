//
//  WatchlistMoviesCoordinator.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/5/22.
//

import UIKit

class WatchlistMoviesCoordinator: Coordinator {

    var navigationController = UINavigationController()
    var parentCoordinator: Coordinator?

    init(tabBarItem: UITabBarItem, parentCoordinator: Coordinator) {
        self.parentCoordinator = parentCoordinator
        let viewController = WatchlistMoviesViewController.instantiate(coordinator: self)
        viewController.tabBarItem = tabBarItem
        navigationController.viewControllers = [viewController]
        navigationController.navigationBar.prefersLargeTitles = true
    }

    func showMovieDetails(_ movie: MovieDetail?) {
        
    }

}
