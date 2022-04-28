//
//  TopRatedMoviesPresenterViewInterface.swift
//  TopRatedMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation
import UIKit

protocol TopRatedMoviesPresenterViewInterface: PresenterViewInterface {
    func viewDidLoad()
    func alertRetryButtonDidTap()
    
    var topRatedMovies: [Movie] { get }
}
