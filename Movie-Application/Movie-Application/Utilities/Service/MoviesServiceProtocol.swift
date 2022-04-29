//
//  MoviesServiceProtocol.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import Foundation

typealias GeneresCompletionHandler = (Result<MoviesGeneres, RequestError>) -> Void
typealias MoviesCompletionHandler = (Result<Movies, RequestError>) -> Void
typealias MovieDetailsCompletionHandler = (Result<MovieDetail, RequestError>) -> Void

protocol MoviesServiceProtocol {
    func getMoviesGeneres(completionHandler: @escaping GeneresCompletionHandler)
    func getTopRatedMovies(page: Int, completionHandler: @escaping MoviesCompletionHandler)
    func getPopularMovies(page: Int, completionHandler: @escaping MoviesCompletionHandler)
    func getMovieDetails(id: Int, completionHandler: @escaping MovieDetailsCompletionHandler)
}
