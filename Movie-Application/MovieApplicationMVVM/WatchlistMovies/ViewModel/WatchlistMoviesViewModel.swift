//
//  WatchlistMoviesViewModel.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/4/22.
//

import UIKit
import Movie_Application

final class WatchlistMoviesViewModel {
    var moviesService: MoviesServiceProtocol
    var movies: (([CoreDataMovie]) -> Void)?
    var errorHandler: ((String) -> Void)?

    private var currentPage = 1
    private var allMovies: [CoreDataMovie]?

    init(moviesService: MoviesServiceProtocol) {
        self.moviesService = moviesService
    }

    // function to get movie image from url that we have
    func getMovieImage(index: Int) -> UIImage {

        if let movies = allMovies {
            return UIImage(data: movies[index].poster) ??  UIImage(systemName: "film.circle")!
        } else {
            return UIImage(systemName: "film.circle")!
        }

    }

    func getMovieTitle(index: Int) -> String {
        allMovies?[index].title ?? ""
    }

    func movieSelected(at index: Int) -> MovieDetail? {
        var movie: MovieDetail?
        if let movies = allMovies {
            moviesService.getMovieDetails(id: movies[index].id) { [weak self] result in
                switch result {
                case .success(let movieDetail):
                    movie = movieDetail

                case .failure(let error):
                    self?.errorHandler?(error.errorDescription ?? error.localizedDescription)
                }
            }
        }
        return movie
    }

    func addToWatchList(index: Int, imageData: Data) {
        if let movies = allMovies {
            let savedMovie = CoreDataMovie(title: movies[index].title,
                                           poster: imageData,
                                           id: movies[index].id,
                                           date: Date.now,
                                           voteAverage: movies[index].voteAverage)
            CoreDataManager().saveNewMovie(savedMovie)
        }
    }

    func getWatchlistMovies() {
        movies?(CoreDataManager().getSavedMovies())
        allMovies = CoreDataManager().getSavedMovies()
    }

    var numberOfMovies: Int {
        return allMovies?.count ?? 0
    }

    var topRatedMovies: [CoreDataMovie] {
        return allMovies ?? []
    }
}
