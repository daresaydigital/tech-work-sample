//
//  HomeCoordinator.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import UIKit

class HomeCoordinator: Coordinator {
    
    var rootViewController: UIViewController?
    
    weak var parentCoordinator: Coordinator?
    
    var childCoordinators: [Coordinator] = []
    
    var navigationController: UINavigationController
    
    init(navigationController: UINavigationController) {
        self.navigationController = navigationController
    }
    
    func start(animated: Bool) {
        let homeViewController = HomeViewController.instantiate(coordinator: self)
        navigationController.pushViewController(homeViewController, animated: animated)
    }
    
    func showFavourites() {
        let favListVC = FavoritesViewController.instantiate(coordinator: self)
        favListVC.favoriteList = UserDefaultData.favoriteList
        navigationController.pushViewController(favListVC, animated: true)
    }
    
    func showDetail(model: MovieModel) {
        let detailVC = MovieDetailViewController.instantiate(coordinator: self)
        detailVC.movie = model
        navigationController.pushViewController(detailVC, animated: true)
    }
    
    deinit {
        print("Removed \(self) from memory")
    }
}
