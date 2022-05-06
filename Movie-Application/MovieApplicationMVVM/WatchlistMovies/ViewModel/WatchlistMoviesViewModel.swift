//
//  WatchlistMoviesViewModel.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/4/22.
//

import UIKit

final class WatchlistMoviesViewModel {
    var moviesService: MoviesServiceProtocol
    var movies: (([CoreDataMovie]) -> Void)?
    var movieDetails: ((MovieDetail) -> Void)?
    var errorHandler: ((String) -> Void)?

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
        if index <= allMovies?.count ?? 0 {
            return allMovies?[index].title ?? ""
        }
        return ""
    }

    func movieSelected(at index: Int) {
        if let movies = allMovies {
            moviesService.getMovieDetails(id: movies[index].id) { [weak self] result in
                switch result {
                case .success(let movieDetail):
                    self?.movieDetails?(movieDetail)
                case .failure(let error):
                    self?.errorHandler?(error.errorDescription ?? error.localizedDescription)
                }
            }
        }
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

    func deleteFromWatchlist(_ index: Int) {
        allMovies?.remove(at: index)
        movies?(allMovies ?? [])
    }

    func getWatchlistMovies() {
        allMovies = CoreDataManager().getSavedMovies()
        movies?(allMovies ?? [])
    }

    func sortByDate() {
        allMovies = allMovies?.sorted(by: { $0.date > $1.date })
        movies?(allMovies ?? [])
    }

    func sortByName() {
        allMovies = allMovies?.sorted(by: { $0.title < $1.title })
        movies?(allMovies ?? [])
    }

    func sortByUserScore() {
        allMovies = allMovies?.sorted(by: { $0.voteAverage > $1.voteAverage })
        movies?(allMovies ?? [])
    }

    func deleteMovies() {
        if let movies = allMovies {
            CoreDataManager().saveMovies(movies: movies)
        }
    }

    var numberOfMovies: Int {
        return allMovies?.count ?? 0
    }

    var topRatedMovies: [CoreDataMovie] {
        return allMovies ?? []
    }
}
