//
//  MoviesCollectionInteractorInterface.swift
//  MoviesCollection
//
//  Created by mohannazakizadeh on 4/26/22.
//

import Foundation
import UIKit

protocol MoviesCollectionInteractorInterface: InteractorPresenterInterface {
    func getMovieImage(for path: String) -> UIImage
}
