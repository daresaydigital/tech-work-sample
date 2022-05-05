//
//  Coordinator.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/3/22.
//

import UIKit

// MARK: - Coordinator
protocol Coordinator: AnyObject {
    var parentCoordinator: Coordinator? { get set }
    func changeTabBarIndex(to index: Int)
}

extension Coordinator {
    func changeTabBarIndex(to index: Int) {}
}
