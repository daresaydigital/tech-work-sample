//
//  MoviesCollectionPresenterViewInterface.swift
//  MoviesCollection
//
//  Created by mohannazakizadeh on 4/26/22.
//

import Foundation
import UIKit

protocol MoviesCollectionPresenterViewInterface: PresenterViewInterface {
    func viewDidLoad()
    func numberOfMovies() -> Int
    func getMovieImage(index: Int, completion: @escaping (UIImage) -> ())
    func getMovieTitle(index: Int) -> String
    func showMovieDetails(_ index: Int)
    func addToWatchList(_ index: Int)
}
