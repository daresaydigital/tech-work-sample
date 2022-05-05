//
//  TopRatedMoviesCoordinator.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/3/22.
//

import UIKit

class TopRatedMoviesCoordinator: Coordinator {
    
    var parentCoordinator: Coordinator?
    var navigationController = UINavigationController()

    init(tabBarItem: UITabBarItem, parentCoordinator: Coordinator) {
        self.parentCoordinator = parentCoordinator
        let viewController = TopRatedMoviesViewController.instantiate(coordinator: self)
        viewController.tabBarItem = tabBarItem
        navigationController.viewControllers = [viewController]
        navigationController.navigationBar.prefersLargeTitles = true
    }

    func showMovieDetails(_ movie: MovieDetail?) {

    }

}
