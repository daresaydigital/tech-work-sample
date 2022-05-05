//
//  PopularMoviesCoordinator.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/4/22.
//

import UIKit

class PopularMoviesCoordinator: Coordinator {

    var navigationController = UINavigationController()
    var parentCoordinator: Coordinator?

    init(tabBarItem: UITabBarItem, parentCoordinator: Coordinator) {
        self.parentCoordinator = parentCoordinator
        let viewController = PopularMoviesViewController.instantiate(coordinator: self)
        viewController.tabBarItem = tabBarItem
        navigationController.viewControllers = [viewController]
        navigationController.navigationBar.prefersLargeTitles = true
    }

    func showMovieDetails(_ movie: MovieDetail?) {
        
    }

}
