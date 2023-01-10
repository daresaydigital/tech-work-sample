//
//  MainCoordinator.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class MainCoordinator: Coordinator {
    var navigationController: UINavigationController?

    func start() {
        let viewController = ViewController()

        navigationController?.setViewControllers([viewController], animated: false)
    }
}
