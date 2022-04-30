//
//  MovieInfoContentPresenter.swift
//  MovieInfoContent
//
//  Created by mohannazakizadeh on 4/29/22.
//

import Foundation
import UIKit

final class MovieInfoContentPresenter: PresenterInterface {

    var router: MovieInfoContentRouterInterface!
    var interactor: MovieInfoContentInteractorInterface!
    weak var view: MovieInfoContentViewInterface!

}

extension MovieInfoContentPresenter: MovieInfoContentPresenterRouterInterface {

}

extension MovieInfoContentPresenter: MovieInfoContentPresenterInteractorInterface {

}

extension MovieInfoContentPresenter: MovieInfoContentPresenterViewInterface {

    func viewDidLoad() {

    }

    func getMovieImage(path: String) -> UIImage? {
        interactor.getMovieImage(path: path)
    }
    
    func addToWatchListTapped(movie: MovieDetail) {
        let url = URL(string: "https://image.tmdb.org/t/p/original/" + (movie.poster ?? ""))!
        guard let data = try? Data(contentsOf: url) else { return }
        let coreDataMovie = CoreDataMovie(title: movie.title, poster: data, id: movie.id, date: Date.now, voteAverage: movie.voteAverage)
        CoreDataManager().saveNewMovie(coreDataMovie)
    }
    
}
