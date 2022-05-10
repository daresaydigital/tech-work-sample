//
//  MovieDetailsPresenter.swift
//  MovieDetails
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import Foundation

final class MovieDetailsPresenter: PresenterInterface {

    var router: MovieDetailsRouterInterface!
    var interactor: MovieDetailsInteractorInterface!
    weak var view: MovieDetailsViewInterface!

}

extension MovieDetailsPresenter: MovieDetailsPresenterRouterInterface {

}

extension MovieDetailsPresenter: MovieDetailsPresenterInteractorInterface {

}

extension MovieDetailsPresenter: MovieDetailsPresenterViewInterface {

    func viewDidLoad() {

    }

}
