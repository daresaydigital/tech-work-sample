//
//  PopularMoviesInteractor.swift
//  PopularMovies
//
//  Created by Mohanna Zakizadeh on 4/23/22.
//

import Foundation
import UIKit

final class PopularMoviesInteractor: InteractorInterface {

    weak var presenter: PopularMoviesPresenterInteractorInterface!
}

extension PopularMoviesInteractor: PopularMoviesInteractorInterface {
    func getPopularMovies(page: Int, completionHandler: @escaping MoviesCompletionHandler) {
        MoviesService.shared.getPopularMovies(page: page) { result in
            completionHandler(result)
        }
    }

    func getMovieImage(for path: String, completion: @escaping (UIImage) -> Void) {

        DispatchQueue.global(qos: .utility).async {
            let url = URL(string: "https://image.tmdb.org/t/p/w300/" + path)!
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
