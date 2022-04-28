//
//  TopRatedMoviesInteractor.swift
//  TopRatedMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation

final class TopRatedMoviesInteractor: InteractorInterface {

    weak var presenter: TopRatedMoviesPresenterInteractorInterface!
}

extension TopRatedMoviesInteractor: TopRatedMoviesInteractorInterface {
    func getTopRatedMovies(completionHandler: @escaping(Result<[Movie], RequestError>) -> Void) {
        MoviesService.shared.getTopRatedMovies { result in
            completionHandler(result)
        }
    }
}
