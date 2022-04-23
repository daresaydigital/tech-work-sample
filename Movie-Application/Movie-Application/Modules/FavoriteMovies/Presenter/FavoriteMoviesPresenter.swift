//
//  FavoriteMoviesPresenter.swift
//  FavoriteMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation

final class FavoriteMoviesPresenter: PresenterInterface {

    var router: FavoriteMoviesRouterInterface!
    var interactor: FavoriteMoviesInteractorInterface!
    weak var view: FavoriteMoviesViewInterface!

}

extension FavoriteMoviesPresenter: FavoriteMoviesPresenterRouterInterface {

}

extension FavoriteMoviesPresenter: FavoriteMoviesPresenterInteractorInterface {

}

extension FavoriteMoviesPresenter: FavoriteMoviesPresenterViewInterface {

    func viewDidLoad() {

    }

}
