//
//  TopRatedMoviesCoordinator.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/3/22.
//

import UIKit

final class TopRatedMoviesCoordinator: Coordinator {
    var parentCoordinator: Coordinator?
    var navigationController: UINavigationController?

    init(tabBarItem: UITabBarItem, parentCoordinator: Coordinator) {
        navigationController = UINavigationController()
        self.parentCoordinator = parentCoordinator
        let viewController = TopRatedMoviesViewController.instantiate(coordinator: self)
        viewController.tabBarItem = tabBarItem
        viewController.topRatedMoviesViewModel = TopRatedMoviesViewModel(moviesService: MoviesService.shared)
        navigationController?.viewControllers = [viewController]
        navigationController?.navigationBar.prefersLargeTitles = true
    }

    func changeTabBarIndex(to index: Int) {
        parentCoordinator?.changeTabBarIndex(to: index)
    }

}
