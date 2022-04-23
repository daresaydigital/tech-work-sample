//
//  TabBarPresenterViewInterface.swift
//  TabBar
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation
import UIKit

protocol TabBarPresenterViewInterface: PresenterViewInterface {
    func viewDidLoad()
    func topRatedMoviesTabDidTap()
    func popularMoviesTabDidTap()
    func favoriteMoviesTabDidTap()
}
