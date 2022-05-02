//
//  PopularMoviesInteractorInterface.swift
//  PopularMovies
//
//  Created by Mohanna Zakizadeh on 4/23/22.
//

import Foundation
import UIKit

protocol PopularMoviesInteractorInterface: InteractorPresenterInterface {
    func getPopularMovies(page: Int, completionHandler: @escaping MoviesCompletionHandler)
    func getMovieImage(for path: String, completion: @escaping (UIImage) -> ())
    
    func getMovieDetails(id: Int, completionHandler: @escaping MovieDetailsCompletionHandler)
}
