//
//  PopularMoviesInteractorInterface.swift
//  PopularMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation
import UIKit

protocol PopularMoviesInteractorInterface: InteractorPresenterInterface {
    func getPopularMovies(page: Int, completionHandler: @escaping(Result<Movies, RequestError>) -> Void)
    func getMovieImage(for path: String, completion: @escaping (UIImage) -> ())
}
