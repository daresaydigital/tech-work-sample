//
//  MoviesServiceProtocol.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import Foundation

typealias MoviesCompletionHandler = (Result<Movies, RequestError>) -> Void
typealias MovieDetailsCompletionHandler = (Result<MovieDetail, RequestError>) -> Void

protocol MoviesServiceProtocol {
    func getTopRatedMovies(page: Int, completionHandler: @escaping MoviesCompletionHandler)
    func getPopularMovies(page: Int, completionHandler: @escaping MoviesCompletionHandler)
    func getMovieDetails(id: Int, completionHandler: @escaping MovieDetailsCompletionHandler)
}
