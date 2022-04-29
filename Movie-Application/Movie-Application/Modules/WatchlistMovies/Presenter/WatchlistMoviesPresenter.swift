//
//  WatchlistMoviesPresenter.swift
//  WatchlistMovies
//
//  Created by mohannazakizadeh on 4/29/22.
//

import Foundation
import UIKit

final class WatchlistMoviesPresenter: PresenterInterface {

    var router: WatchlistMoviesRouterInterface!
    var interactor: WatchlistMoviesInteractorInterface!
    weak var view: WatchlistMoviesViewInterface!
    
    private var movies: [CoreDataMovie]?
    private var deletedMovies = [CoreDataMovie]()

    init() {
        // in order to scroll top top when user tapped te tab bar again
        NotificationCenter.default.addObserver(forName: TabBarViewContorller.tabBarDidTapNotification, object: nil, queue: nil) { notification in
            self.view.scrollToTop()
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
        view.reloadCollectionView()
    }
    
    // function to get movie image from url that we have
    func getMovieImage(index: Int) -> UIImage {
        
        if let movies = movies {
            return UIImage(data: movies[index].poster) ??  UIImage(systemName: "film.circle")!
        }
         else {
            return UIImage(systemName: "film.circle")!
        }
        
    }
    
    func getMovieTitle(index: Int) -> String {
        movies?[index].title ?? ""
    }
    
    func showMovieDetails(_ index: Int) {
        if let movies = movies {
            router.showMovieDetails(id: movies[index].id)
        }
    }
    
    func deletefromWatchList(_ index: Int) {
        self.movies?.remove(at: index)
        view.reloadCollectionView()
    }
    
    func deleteMovies() {
        CoreDataManager().saveMovies(movies: movies ?? [])
    }
    
    var numberOfMovies: Int {
        return movies?.count ?? 0
    }
    
    var watchlistMovies: [CoreDataMovie] {
        get {
            return movies ?? []
        }
    }

}
