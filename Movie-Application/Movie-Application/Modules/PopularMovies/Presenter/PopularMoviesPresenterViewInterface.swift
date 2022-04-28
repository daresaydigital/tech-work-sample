//
//  PopularMoviesPresenterViewInterface.swift
//  PopularMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation
import UIKit

protocol PopularMoviesPresenterViewInterface: PresenterViewInterface {
    func viewDidLoad()
    func alertRetryButtonDidTap()
    func getMovieImage(index: Int, completion: @escaping (UIImage) -> ())
    func getMovieTitle(index: Int) -> String
    func showMovieDetails(_ index: Int)
    func addToWatchList(_ index: Int)
    func getPopularMovies()
    
    var numberOfMovies: Int { get }
    var popularMovies: [Movie] { get }
}
