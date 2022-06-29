//
//  AppCoordinator.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-27.
//

import UIKit

protocol AppCoordinatorProtocol: Coordinator {
    func showMainFlow()
}

final class AppCoordinator: NSObject, AppCoordinatorProtocol {
    weak var finishDelegate: CoordinatorFinishDelegate?
    
    weak var parentCoordinator: Coordinator?
    
    var type: CoordinatorType { .app }
    
    var childCoordinators: [Coordinator] = []
    
    var navigationController: UINavigationController
    
    required init(_ navigationController: UINavigationController) {
        self.navigationController = navigationController
        navigationController.setNavigationBarHidden(true, animated: false)
    }
    
    func start(animated: Bool) {
        showMainFlow()
    }

    func showMainFlow() {
        let moviesCoordinator = MoviesCoordinator(navigationController)
        moviesCoordinator.finishDelegate = self
        moviesCoordinator.start()
        childCoordinators.append(moviesCoordinator)
    }
}

extension AppCoordinator: CoordinatorFinishDelegate {
    func coordinatorDidFinish(childCoordinator: Coordinator) {
    
    }
}
