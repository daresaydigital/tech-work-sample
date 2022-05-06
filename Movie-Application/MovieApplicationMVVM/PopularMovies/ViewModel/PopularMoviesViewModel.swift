//
//  PopularMoviesViewModel.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/4/22.
//

import UIKit

final class PopularMoviesViewModel {
    var moviesService: MoviesServiceProtocol
    var movies: (([Movie]) -> Void)?
    var movieDetails: ((MovieDetail) -> Void)?
    var errorHandler: ((String) -> Void)?

    private var currentPage = 1
    private var allMovies: [Movie]?

    init(moviesService: MoviesServiceProtocol) {
        self.moviesService = moviesService
    }

    func alertRetryButtonDidTap() {
        getPopularMovies()
    }

    // function to get movie image from url that we have
    func getMovieImage(index: Int, completion: @escaping (UIImage) -> Void) {

        if let movies = allMovies {
            if let path = movies[index].poster {
                return moviesService.getMovieImage(for: path, completion: completion)
            }
        } else {
            completion(UIImage(systemName: "film.circle")!)
        }

    }

    func getMovieTitle(index: Int) -> String {
        allMovies?[index].title ?? ""
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

    func getPopularMovies() {
        // movie data base gives 500 pages max.
        if currentPage <= 500 {
            moviesService.getPopularMovies(page: currentPage) { result in
                switch result {
                case .success(let moviesData):
                    if self.currentPage == 1 {
                        self.allMovies = moviesData.results
                    } else {
                        self.allMovies! += moviesData.results
                    }
                    self.currentPage += 1
                    self.movies?(moviesData.results)

                case .failure(let error):
                    self.errorHandler?(error.errorDescription ?? error.localizedDescription)
                }
            }
        }

    }

    func getSavedMovies() -> [CoreDataMovie] {
        CoreDataManager().getSavedMovies()
    }

    var numberOfMovies: Int {
        return allMovies?.count ?? 0
    }

    var topRatedMovies: [Movie] {
        return allMovies ?? []
    }
}
