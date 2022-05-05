//
//  AppCoordinator.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/3/22.
//

import UIKit

class AppCoordinator: NSObject, Coordinator {
    
    // Since AppCoordinator is top of all coordinators of our app, it has no parent and is nil.
    var parentCoordinator: Coordinator?
    
    let window: UIWindow?
    var tabBarController: UITabBarController?

    var childCoordinators = [Coordinator]()

    init(window: UIWindow?) {
        self.window = window
        super.init()
        tabBarController = MainTabBarController(parentCoordinator: self)
    }

    func start() {
        guard let window = window else { return }

        window.rootViewController = tabBarController
        window.makeKeyAndVisible()
    }
    
    func changeTabBarIndex(to index: Int) {
        tabBarController?.selectedIndex = index
    }
}
