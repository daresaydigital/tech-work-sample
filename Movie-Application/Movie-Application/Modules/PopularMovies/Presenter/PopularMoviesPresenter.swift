//
//  PopularMoviesPresenter.swift
//  PopularMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation

final class PopularMoviesPresenter: PresenterInterface {

    var router: PopularMoviesRouterInterface!
    var interactor: PopularMoviesInteractorInterface!
    weak var view: PopularMoviesViewInterface!
    
    private var movies: [Movie]?
    
    private func getPopularMovies() {
        interactor.getPopularMovies { result in
            switch result {
            case .success(let moviesData):
                self.movies = moviesData.results
                self.view.loadCollectionView(with: self.movies ?? [])
                
            case .failure(let error):
                self.view.showError(with: error)
            }
        }
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
    
    var popularMovies: [Movie] {
        get {
            return movies ?? []
        }
    }
    

}
