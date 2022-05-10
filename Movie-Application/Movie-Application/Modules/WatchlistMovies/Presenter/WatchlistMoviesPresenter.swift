//
//  WatchlistMoviesPresenter.swift
//  WatchlistMovies
//
//  Created by  Mohanna Zakizadeh on 4/29/22.
//

import Foundation
import UIKit

final class WatchlistMoviesPresenter: PresenterInterface {

    var router: WatchlistMoviesRouterInterface!
    var interactor: WatchlistMoviesInteractorInterface!
    weak var view: WatchlistMoviesViewInterface!

    var movies: [CoreDataMovie]?
    private var deletedMovies = [CoreDataMovie]()

    init() {
        // in order to scroll top top when user tapped te tab bar again
        NotificationCenter.default.addObserver(
            forName: TabBarViewContorller.tabBarDidTapNotification,
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

extension WatchlistMoviesPresenter: WatchlistMoviesPresenterRouterInterface {

}

extension WatchlistMoviesPresenter: WatchlistMoviesPresenterInteractorInterface {

}

extension WatchlistMoviesPresenter: WatchlistMoviesPresenterViewInterface {

    func viewDidLoad() {
        getWatchlistMovies()
    }

    func getWatchlistMovies() {
        movies = CoreDataManager().getSavedMovies()
        if let movies = movies {
            if movies.isEmpty {
                view.setWatchlistEmptyContainerisHidden(to: false)
            } else {
                view.setWatchlistEmptyContainerisHidden(to: true)
                view.reloadCollectionView()
            }
        }
    }

    // function to get movie image from url that we have
    func getMovieImage(index: Int) -> UIImage {

        if let movies = movies {
            return UIImage(data: movies[index].poster) ??  UIImage(systemName: "film.circle")!
        } else {
            return UIImage(systemName: "film.circle")!
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
                    self?.view.showError(with: error, index: index)
                }
            }
        }

    }

    func deletefromWatchList(_ index: Int) {
        self.movies?.remove(at: index)
        view.reloadCollectionView()

        if movies!.isEmpty {
            view.setWatchlistEmptyContainerisHidden(to: false)
        }
    }

    func deleteMovies() {
        if let movies = movies {
            CoreDataManager().saveMovies(movies: movies)
        }
    }

    func alertRetryButtonDidTap(_ index: Int) {
        if let movies = movies {
            interactor.getMovieDetails(id: movies[index].id) { [weak self] result in
                switch result {
                case .success(let movie):
                    self?.router.showMovieDetails(movie)

                case .failure(let error):
                    self?.view.showError(with: error, index: index)
                }

            }
        }
    }

    func sortByDate() {
        movies = movies?.sorted(by: { $0.date > $1.date })
        view.reloadCollectionView()
    }

    func sortByName() {
        movies = movies?.sorted(by: { $0.title > $1.title })
        view.reloadCollectionView()
    }

    func sortByUserScore() {
        movies = movies?.sorted(by: { $0.voteAverage > $1.voteAverage })
        view.reloadCollectionView()
    }

    func browseMoviesDidTap() {
        router.showPopularMovies()
    }

    func configureContextMenu(_ index: Int) -> UIContextMenuConfiguration {
        let context = UIContextMenuConfiguration(identifier: nil, previewProvider: nil) { (_) -> UIMenu? in

            let viewDetails = UIAction(title: "View Details",
                                       image: UIImage(systemName: "text.below.photo.fill"),
                                       identifier: nil,
                                       discoverabilityTitle: nil, state: .off) { (_) in
                self.movieSelected(at: index)
            }
            let remove = UIAction(title: "Remove from Watchlist",
                                  image: UIImage(systemName: "trash"),
                                  identifier: nil,
                                  discoverabilityTitle: nil,
                                  attributes: .destructive, state: .off) { (_) in
                self.deletefromWatchList(index)
            }

            return UIMenu(title: self.getMovieTitle(index: index),
                          image: nil, identifier: nil,
                          options: UIMenu.Options.displayInline, children: [viewDetails, remove])

        }
        return context
    }

    var numberOfMovies: Int {
        return movies?.count ?? 0
    }

    var watchlistMovies: [CoreDataMovie] {
        return movies ?? []
    }

}
