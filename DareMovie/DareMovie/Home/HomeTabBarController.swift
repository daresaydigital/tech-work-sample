//
//  HomeTabBarController.swift
//  DareMovie
//
//  Created by Emran on 1/2/22.
//

import UIKit

class HomeTabBarController: UITabBarController {
    init() {
        super.init(nibName: nil, bundle: nil)
        
        let popularVC = PopularViewController(.pupuar)
        let topRatedVC = PopularViewController(.topRated)

        let popularTabBarItem = UITabBarItem(title: "Popular", image: UIImage(systemName: "suit.heart.fill"), tag: 0)
        let topRatedTabBarItem = UITabBarItem(title: "Top Rated", image: UIImage(systemName: "number"), tag: 1)

        popularVC.tabBarItem = popularTabBarItem
        topRatedVC.tabBarItem = topRatedTabBarItem
        self.viewControllers = [popularVC, topRatedVC]
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
