//
//  WatchlistMoviesInteractorInterface.swift
//  WatchlistMovies
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import Foundation
import UIKit

protocol WatchlistMoviesInteractorInterface: InteractorPresenterInterface {
    func getMovieDetails(id: Int, completionHandler: @escaping MovieDetailsCompletionHandler)
}
