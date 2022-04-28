//
//  MoviesCollectionPresenter.swift
//  MoviesCollection
//
//  Created by mohannazakizadeh on 4/26/22.
//

import Foundation
import UIKit

final class MoviesCollectionPresenter: PresenterInterface {

    var router: MoviesCollectionRouterInterface!
    var interactor: MoviesCollectionInteractorInterface!
    weak var view: MoviesCollectionViewInterface!

    var movies: [Movie]
    private let cache = NSCache<NSNumber, UIImage>()
    
    init(movies: [Movie]) {
        self.movies = movies
    }
}

extension MoviesCollectionPresenter: MoviesCollectionPresenterRouterInterface {

}

extension MoviesCollectionPresenter: MoviesCollectionPresenterInteractorInterface {

}

extension MoviesCollectionPresenter: MoviesCollectionPresenterViewInterface {

    func viewDidLoad() {

    }
    
    func numberOfMovies() -> Int {
        movies.count
    }
    
    // function to get movie image from url that we have
    func getMovieImage(index: Int, completion: @escaping (UIImage) -> ()) {
        
        if let path = movies[index].poster {
            return interactor.getMovieImage(for: path, completion: completion)
        } else {
            completion(UIImage(systemName: "film.circle")!)
        }
        
    }
    
    func getMovieTitle(index: Int) -> String {
        movies[index].title
    }
    
    func showMovieDetails(_ index: Int) {
        router.showMovieDetails(id: movies[index].id)
    }
    
    func addToWatchList(_ index: Int) {
        let movie = movies[index]
        CoreDataManager().saveNewMovie(movie)
    }
}
