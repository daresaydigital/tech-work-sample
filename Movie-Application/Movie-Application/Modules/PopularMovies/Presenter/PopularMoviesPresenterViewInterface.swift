//
//  PopularMoviesPresenterViewInterface.swift
//  PopularMovies
//
//  Created by Mohanna Zakizadeh on 4/23/22.
//

import Foundation
import UIKit

protocol PopularMoviesPresenterViewInterface: PresenterViewInterface {
    func viewDidLoad()
    func alertRetryButtonDidTap()
    func getMovieImage(index: Int, completion: @escaping (UIImage) -> ())
    func getMovieTitle(index: Int) -> String
    func movieSelected(at index: Int)
    func addToWatchList(index: Int, imageData: Data)
    func getPopularMovies()
    func getSavedMovies() -> [CoreDataMovie]
    
    var numberOfMovies: Int { get }
    var popularMovies: [Movie] { get }
}
