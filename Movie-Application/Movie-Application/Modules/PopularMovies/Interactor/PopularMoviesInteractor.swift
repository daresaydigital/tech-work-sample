//
//  PopularMoviesInteractor.swift
//  PopularMovies
//
//  Created by mohannazakizadeh on 4/23/22.
//

import Foundation
import UIKit

final class PopularMoviesInteractor: InteractorInterface {

    weak var presenter: PopularMoviesPresenterInteractorInterface!
}

extension PopularMoviesInteractor: PopularMoviesInteractorInterface {
    func getPopularMovies(page: Int, completionHandler: @escaping(Result<Movies, RequestError>) -> Void) {
        MoviesService.shared.getPopularMovies(page: page) { result in
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
    
}
