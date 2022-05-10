//
//  MovieInfoContentPresenterViewInterface.swift
//  MovieInfoContent
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import Foundation
import UIKit

protocol MovieInfoContentPresenterViewInterface: PresenterViewInterface {
    func viewDidLoad()
    func getMovieImage(path: String) -> UIImage?
    func addToWatchListTapped(movie: MovieDetail)
}
