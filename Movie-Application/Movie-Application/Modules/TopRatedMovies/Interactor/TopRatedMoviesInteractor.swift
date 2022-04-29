//
//  TopRatedMoviesInteractor.swift
//  TopRatedMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation
import UIKit

final class TopRatedMoviesInteractor: InteractorInterface {

    weak var presenter: TopRatedMoviesPresenterInteractorInterface!
}

extension TopRatedMoviesInteractor: TopRatedMoviesInteractorInterface {
    func getTopRatedMovies(page: Int, completionHandler: @escaping MoviesCompletionHandler) {
        MoviesService.shared.getTopRatedMovies(page: page) { result in
            completionHandler(result)
        }
    }
    
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
    
    func getMovieDetails(id: Int, completionHandler: @escaping MovieDetailsCompletionHandler) {
        MoviesService.shared.getMovieDetails(id: id) { result in
            completionHandler(result)
        }
    }
}
