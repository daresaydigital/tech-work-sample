//
//  PopularMoviesPresenter.swift
//  PopularMovies
//
//  Created by Mohanna Zakizadeh on 4/23/22.
//

import Foundation
import UIKit

final class PopularMoviesPresenter: PresenterInterface {

    var router: PopularMoviesRouterInterface!
    var interactor: PopularMoviesInteractorInterface!
    weak var view: PopularMoviesViewInterface!
    
    var movies: [Movie]?
    private var currentPage = 1
    
    init() {
        // in order to scroll top top when user tapped te tab bar again
        NotificationCenter.default.addObserver(forName: TabBarViewContorller.tabBarDidTapNotification, object: nil, queue: nil) { notification in
            if let view = self.view {
                view.scrollToTop()
            }
        }
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

}

extension PopularMoviesPresenter: PopularMoviesPresenterRouterInterface {

}

extension PopularMoviesPresenter: PopularMoviesPresenterInteractorInterface {

}

extension PopularMoviesPresenter: PopularMoviesPresenterViewInterface {
    
    func viewDidLoad() {
        getPopularMovies()
    }

    func alertRetryButtonDidTap() {
        getPopularMovies()
    }
    
    func getPopularMovies() {
        // movie data base gives 500 pages max.
        if currentPage <= 500 {
            interactor.getPopularMovies(page: currentPage) { result in
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
            let savedMovie = CoreDataMovie(title: movies[index].title, poster: imageData, id: movies[index].id, date: Date.now, voteAverage: movies[index].voteAverage)
            CoreDataManager().saveNewMovie(savedMovie)
        }
    }
    
    func getSavedMovies() -> [CoreDataMovie] {
        CoreDataManager().getSavedMovies()
    }
    
    var numberOfMovies: Int {
        return movies?.count ?? 0
    }
    
    var popularMovies: [Movie] {
        get {
            return movies ?? []
        }
    }
    

}
