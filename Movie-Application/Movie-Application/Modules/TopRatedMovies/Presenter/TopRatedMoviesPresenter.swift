//
//  TopRatedMoviesPresenter.swift
//  TopRatedMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation

final class TopRatedMoviesPresenter: PresenterInterface {

    var router: TopRatedMoviesRouterInterface!
    var interactor: TopRatedMoviesInteractorInterface!
    weak var view: TopRatedMoviesViewInterface!
    
    private var movies: [Movie]?

    private func getTopRatedMovies() {
        interactor.getTopRatedMovies { result in
            switch result {
            case .success(let moviesData):
                self.movies = moviesData
                
            case .failure(let error):
                self.view.showError(with: error)
            }
        }
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

}
