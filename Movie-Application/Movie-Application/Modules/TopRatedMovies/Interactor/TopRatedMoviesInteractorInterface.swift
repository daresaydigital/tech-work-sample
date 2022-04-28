//
//  TopRatedMoviesInteractorInterface.swift
//  TopRatedMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation

protocol TopRatedMoviesInteractorInterface: InteractorPresenterInterface {
    func getTopRatedMovies(completionHandler: @escaping(Result<Movies, RequestError>) -> Void)
}
