//
//  MovieInfoContentInteractor.swift
//  MovieInfoContent
//
//  Created by mohannazakizadeh on 4/29/22.
//

import Foundation
import UIKit

final class MovieInfoContentInteractor: InteractorInterface {

    weak var presenter: MovieInfoContentPresenterInteractorInterface!
}

extension MovieInfoContentInteractor: MovieInfoContentInteractorInterface {
    
    func getMovieImage(path: String) -> UIImage? {
        let url = URL(string: "https://image.tmdb.org/t/p/original/" + path)!
        guard let data = try? Data(contentsOf: url) else { return UIImage(systemName: "film.circle") }
        let image = UIImage(data: data)
        return image
    }
}
