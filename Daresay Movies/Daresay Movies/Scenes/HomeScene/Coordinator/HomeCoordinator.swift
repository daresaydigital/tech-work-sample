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
    
    deinit {
        print("Removed \(self) from memory")
    }
}
