//
//  MovieTabBarController.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-27.
//

import UIKit

class MovieTabBarController: UITabBarController {
    //MARK: Lifecycle
    override func viewDidLoad() {
        super.viewDidLoad()
        configure()
        viewControllers = [createMovieStoreNC(), createDownloadmovieNC()]
    }
    
    //MARK: Helpers
    private func createMovieStoreNC() -> UINavigationController {
        let controller = MovieVC()
        let nav = UINavigationController(rootViewController: controller)
        nav.tabBarItem = UITabBarItem(title: "Movies", image: SFSymbols.film, selectedImage: SFSymbols.filmSelected)
        return nav
    }
    
    private func createDownloadmovieNC() -> UINavigationController {
        let controller = DownloadedVC()
        let nav = UINavigationController(rootViewController: controller)
        nav.tabBarItem = UITabBarItem(title: "Downloads", image: SFSymbols.downloaded, selectedImage: SFSymbols.downloadedSelected)
        return nav
    }
    
    private func configure() {
        if #available(iOS 15, *) {
            let appearance = UITabBarAppearance()
            appearance.configureWithOpaqueBackground()
            appearance.backgroundColor = .black
            UITabBar.appearance().standardAppearance = appearance
            UITabBar.appearance().tintColor = .systemGray3
            
            self.tabBar.standardAppearance = appearance
            self.tabBar.scrollEdgeAppearance = appearance
        } else {
            let appearance = UITabBar.appearance()
            appearance.backgroundColor = .black
            appearance.tintColor = .systemGray3
            appearance.alpha = 0.04
        }
    }
}
