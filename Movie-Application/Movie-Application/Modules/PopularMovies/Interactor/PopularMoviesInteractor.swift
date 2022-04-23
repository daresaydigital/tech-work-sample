//
//  PopularMoviesInteractor.swift
//  PopularMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation

final class PopularMoviesInteractor: InteractorInterface {

    weak var presenter: PopularMoviesPresenterInteractorInterface!
}

extension PopularMoviesInteractor: PopularMoviesInteractorInterface {

}
