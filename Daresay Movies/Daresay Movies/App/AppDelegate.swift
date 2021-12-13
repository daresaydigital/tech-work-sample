//
//  AppDelegate.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/13/21.
//

import UIKit
import CoreData

class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    var appCoordinator: AppCoordinator!

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        // I Used Coordinator pattern for flow of app.
        setupCoordinator()
        
        return true
    }

    fileprivate func setupCoordinator() {
        window = UIWindow(frame: UIScreen.main.bounds)
        let navController = UINavigationController()

        appCoordinator = AppCoordinator(navigationController: navController, window: window)
        appCoordinator.start()
    }
    
}
