//
//  PopularMoviesCoordinator.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/4/22.
//

import UIKit

final class PopularMoviesCoordinator: Coordinator {
    var navigationController: UINavigationController?
    var parentCoordinator: Coordinator?

    init(tabBarItem: UITabBarItem, parentCoordinator: Coordinator) {
        navigationController = UINavigationController()
        self.parentCoordinator = parentCoordinator
        let viewController = PopularMoviesViewController.instantiate(coordinator: self)
        viewController.tabBarItem = tabBarItem
        viewController.popularMoviesViewModel = PopularMoviesViewModel(moviesService: MoviesService.shared)
        navigationController?.viewControllers = [viewController]
        navigationController?.navigationBar.prefersLargeTitles = true
    }

    func changeTabBarIndex(to index: Int) {
        parentCoordinator?.changeTabBarIndex(to: index)
    }

}
