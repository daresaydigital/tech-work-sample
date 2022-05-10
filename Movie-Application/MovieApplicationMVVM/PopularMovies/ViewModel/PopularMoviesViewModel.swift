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

    // movie data base gives 500 pages max.
    private var maxPages: Int = 500

    private var currentPage = 1
    private var allMovies: [Movie]?

    init(moviesService: MoviesServiceProtocol) {
        self.moviesService = moviesService
    }

    var numberOfMovies: Int {
        return allMovies?.count ?? 0
    }

    var topRatedMovies: [Movie] {
        return allMovies ?? []
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

        if currentPage <= maxPages {
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

    func configureContextMenu(index: Int, imageData: Data) -> UIContextMenuConfiguration {
        // prevents from adding repititious movies to watch list
        if !self.getSavedMovies().contains(where: { $0.title == self.getMovieTitle(index: index)}) {
            let context = UIContextMenuConfiguration(identifier: nil, previewProvider: nil) { (_) -> UIMenu? in

                let viewDetails = UIAction(title: "View Details",
                                           image: UIImage(systemName: "text.below.photo.fill"),
                                           identifier: nil,
                                           discoverabilityTitle: nil, state: .off) { (_) in
                    self.self.movieSelected(at: index)
                }

                let addToWatchList = UIAction(title: "Add to Watchlist",
                                              image: UIImage(systemName: "bookmark"),
                                              identifier: nil,
                                              discoverabilityTitle: nil, state: .off) { (_) in
                    self.addToWatchList(index: index, imageData: imageData)
                }

                return UIMenu(title: self.getMovieTitle(index: index),
                              image: nil, identifier: nil,
                              options: UIMenu.Options.displayInline,
                              children: [addToWatchList, viewDetails])

            }
            return context

        } else {
            let context = UIContextMenuConfiguration(identifier: nil, previewProvider: nil) { (_) -> UIMenu? in

                let viewDetails = UIAction(title: "View Details",
                                           image: UIImage(systemName: "text.below.photo.fill"),
                                           identifier: nil, discoverabilityTitle: nil,
                                           state: .off) { (_) in
                    self.movieSelected(at: index)
                }

                let addToWatchList = UIAction(title: "Added to Watchlist",
                                              image: UIImage(systemName: "bookmark.fill"),
                                              identifier: nil, discoverabilityTitle: nil,
                                              state: .off) { (_) in

                }

                return UIMenu(title: self.getMovieTitle(index: index),
                              image: nil, identifier: nil,
                              options: UIMenu.Options.displayInline,
                              children: [addToWatchList, viewDetails])

            }
            return context
        }
    }
}
