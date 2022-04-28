//
//  TopRatedMoviesPresenter.swift
//  TopRatedMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation
import UIKit

final class TopRatedMoviesPresenter: PresenterInterface {

    var router: TopRatedMoviesRouterInterface!
    var interactor: TopRatedMoviesInteractorInterface!
    weak var view: TopRatedMoviesViewInterface!
    
    private var movies: [Movie]?
    private var currentPage = 1
    
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
    
    var topRatedMovies: [Movie] {
        get {
            return movies ?? []
        }
    }
    
    // function to get movie image from url that we have
    func getMovieImage(index: Int, completion: @escaping (UIImage) -> ()) {
        
        if let movies = movies {
            if let path = movies[index].poster {
                return interactor.getMovieImage(for: path, completion: completion)
            }
        }
         else {
            completion(UIImage(systemName: "film.circle")!)
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
    
    func addToWatchList(_ index: Int) {
        if let movies = movies {
            CoreDataManager().saveNewMovie(movies[index])
        }
    }
    
    var numberOfMovies: Int {
        return movies?.count ?? 0
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

}
