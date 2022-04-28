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
    func getMovieImage(for path: String, completion: @escaping (UIImage) -> ()) {
        
        DispatchQueue.global(qos: .utility).async {
            let url = URL(string: "https://image.tmdb.org/t/p/original/" + path)!
            guard let data = try? Data(contentsOf: url) else { return }
            let image = UIImage(data: data) ?? UIImage(systemName: "film.circle")!
            
            DispatchQueue.main.async {
                completion(image)
            }
        }
        
    }
    
}
