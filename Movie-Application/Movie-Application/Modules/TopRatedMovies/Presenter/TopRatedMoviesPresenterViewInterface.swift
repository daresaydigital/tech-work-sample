//
//  TopRatedMoviesPresenterViewInterface.swift
//  TopRatedMovies
//
//  Created by Mohanna Zakizadeh on 4/23/22.
//

import Foundation
import UIKit

protocol TopRatedMoviesPresenterViewInterface: PresenterViewInterface {
    func viewDidLoad()
    func alertRetryButtonDidTap()
    func getMovieImage(index: Int, completion: @escaping (UIImage) -> Void)
    func getMovieTitle(index: Int) -> String
    func movieSelected(at index: Int)
    func addToWatchList(index: Int, imageData: Data)
    func getTopRatedMovies()
    func getSavedMovies() -> [CoreDataMovie]
    func configureContextMenu(index: Int, imageData: Data) -> UIContextMenuConfiguration

    var topRatedMovies: [Movie] { get }
    var numberOfMovies: Int { get }
}
