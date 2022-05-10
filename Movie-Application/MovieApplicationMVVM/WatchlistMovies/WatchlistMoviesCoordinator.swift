//
//  WatchlistMoviesCoordinator.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/5/22.
//

import UIKit

final class WatchlistMoviesCoordinator: Coordinator {

    var navigationController: UINavigationController?
    var parentCoordinator: Coordinator?

    init(tabBarItem: UITabBarItem, parentCoordinator: Coordinator) {
        navigationController = UINavigationController()
        self.parentCoordinator = parentCoordinator
        let viewController = WatchlistMoviesViewController.instantiate(coordinator: self)
        viewController.tabBarItem = tabBarItem
        viewController.viewModel = WatchlistMoviesViewModel(moviesService: MoviesService.shared)
        navigationController?.viewControllers = [viewController]
        navigationController?.navigationBar.prefersLargeTitles = true
    }

    deinit {
        print("removed \(self) from memory")
    }

    func changeTabBarIndex(to index: Int) {
        parentCoordinator?.changeTabBarIndex(to: index)
    }

}
