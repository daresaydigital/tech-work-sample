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
    func getMovieImage(index: Int, completion: @escaping (UIImage) -> ())
    func getMovieTitle(index: Int) -> String
    func showMovieDetails(_ index: Int)
    func addToWatchList(_ index: Int)
    func getTopRatedMovies()
    
    var topRatedMovies: [Movie] { get }
    var numberOfMovies: Int { get }
}
