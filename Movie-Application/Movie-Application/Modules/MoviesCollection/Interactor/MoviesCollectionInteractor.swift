//
//  MoviesCollectionInteractor.swift
//  MoviesCollection
//
//  Created by mohannazakizadeh on 4/26/22.
//

import Foundation
import UIKit

final class MoviesCollectionInteractor: InteractorInterface {

    weak var presenter: MoviesCollectionPresenterInteractorInterface!
    var imageData: Data?
}

extension MoviesCollectionInteractor: MoviesCollectionInteractorInterface {
    func getMovieImage(for path: String) -> UIImage {
        var image = UIImage()
        MoviesService.shared.getMovieImage(from: path) { data, response, error in
            guard let data = data, error == nil else { return }
            image = UIImage(data: data) ?? UIImage(systemName: "film.circle")!
        }
        return image
    }
    
}
