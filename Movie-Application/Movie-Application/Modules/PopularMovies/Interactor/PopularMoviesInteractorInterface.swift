//
//  PopularMoviesInteractorInterface.swift
//  PopularMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation

protocol PopularMoviesInteractorInterface: InteractorPresenterInterface {
    func getPopularMovies(completionHandler: @escaping(Result<Movies, RequestError>) -> Void)
}
