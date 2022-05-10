//
//  TopRatedMoviesPresenter.swift
//  TopRatedMovies
//
//  Created by Mohanna Zakizadeh on 4/23/22.
//

import Foundation
import UIKit

final class TopRatedMoviesPresenter: PresenterInterface {

    var router: TopRatedMoviesRouterInterface!
    var interactor: TopRatedMoviesInteractorInterface!
    weak var view: TopRatedMoviesViewInterface!

    var movies: [Movie]?
    private var currentPage = 1

    init() {
        // in order to scroll top top when user tapped te tab bar again
        NotificationCenter.default.addObserver(forName: TabBarViewContorller.tabBarDidTapNotification,
                                               object: nil, queue: nil) { (_) in
            if let view = self.view {
                view.scrollToTop()
            }
        }
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}

extension TopRatedMoviesPresenter: TopRatedMoviesPresenterRouterInterface {

}

extension TopRatedMoviesPresenter: TopRatedMoviesPresenterInteractorInterface {

}

extension TopRatedMoviesPresenter: TopRatedMoviesPresenterViewInterface {

    func viewDidLoad() {
        getTopRatedMovies()
    }

    func alertRetryButtonDidTap() {
        getTopRatedMovies()
    }

    // function to get movie image from url that we have
    func getMovieImage(index: Int, completion: @escaping (UIImage) -> Void) {

        if let movies = movies {
            if let path = movies[index].poster {
                return interactor.getMovieImage(for: path, completion: completion)
            }
        } else {
            completion(UIImage(systemName: "film.circle")!)
        }

    }
    func getMovieTitle(index: Int) -> String {
        movies?[index].title ?? ""
    }

    func movieSelected(at index: Int) {
        if let movies = movies {
            interactor.getMovieDetails(id: movies[index].id) { [weak self] result in
                switch result {
                case .success(let movie):
                    self?.router.showMovieDetails(movie)

                case .failure(let error):
                    self?.view.showError(with: error)
                }
            }
        }

    }

    func addToWatchList(index: Int, imageData: Data) {
        if let movies = movies {
            let savedMovie = CoreDataMovie(title: movies[index].title,
                                           poster: imageData,
                                           id: movies[index].id,
                                           date: Date.now,
                                           voteAverage: movies[index].voteAverage)
            CoreDataManager().saveNewMovie(savedMovie)
        }
    }

    func getTopRatedMovies() {
        // movie data base gives 495 pages max.
        if currentPage < 496 {
            interactor.getTopRatedMovies(page: currentPage) { result in
                switch result {
                case .success(let moviesData):

                    if self.currentPage == 1 {
                        self.movies = moviesData.results
                    } else {
                        self.movies! += moviesData.results
                    }

                    self.view.reloadCollectionView()
                    self.currentPage += 1

                case .failure(let error):
                    self.view.showError(with: error)
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
                    self.movieSelected(at: index)
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

    var numberOfMovies: Int {
        return movies?.count ?? 0
    }

    var topRatedMovies: [Movie] {
        return movies ?? []
    }

}
