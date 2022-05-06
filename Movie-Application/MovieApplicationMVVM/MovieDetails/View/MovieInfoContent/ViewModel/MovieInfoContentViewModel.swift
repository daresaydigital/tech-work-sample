//
//  MovieInfoContentViewModel.swift
//  MovieApplicationMVVM
//
//  Created by Mohanna Zakizadeh on 5/5/22.
//

import UIKit

final class MovieInfoContentViewModel {

    func getMovieImage(path: String) -> UIImage? {
        let url = URL(string: "https://image.tmdb.org/t/p/original/" + path)!
        guard let data = try? Data(contentsOf: url) else { return UIImage(systemName: "film.circle") }
        let image = UIImage(data: data)
        return image
    }

    func addToWatchListTapped(movie: MovieDetail) {
        let url = URL(string: "https://image.tmdb.org/t/p/original/" + (movie.poster ?? ""))!
        guard let data = try? Data(contentsOf: url) else { return }
        let coreDataMovie = CoreDataMovie(title: movie.title,
                                          poster: data,
                                          id: movie.id,
                                          date: Date.now,
                                          voteAverage: movie.voteAverage)
        CoreDataManager().saveNewMovie(coreDataMovie)
    }
}
