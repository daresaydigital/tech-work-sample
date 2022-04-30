//
//  WatchlistMoviesInteractor.swift
//  WatchlistMovies
//
//  Created by mohannazakizadeh on 4/29/22.
//

import Foundation
import UIKit

final class WatchlistMoviesInteractor: InteractorInterface {

    weak var presenter: WatchlistMoviesPresenterInteractorInterface!
}

extension WatchlistMoviesInteractor: WatchlistMoviesInteractorInterface {

    func getMovieDetails(id: Int, completionHandler: @escaping MovieDetailsCompletionHandler) {
        MoviesService.shared.getMovieDetails(id: id) { result in
            completionHandler(result)
        }
    }
    
}
