//
//  DetailView+ViewModel.swift
//  Movies
//
//  Created by Richard Segerblom on 2022-12-21.
//

import Foundation

extension DetailView {
    struct ViewModel {
        let title: String
        let releseYear: String
        let movePosterURL: URL?
        let description: String
        
        init(movie: Movie) {
            title = movie.title
            releseYear = String(movie.releaseDate.split(separator: "-").first ?? "")
            movePosterURL = URL(string: "https://image.tmdb.org/t/p/w185\(movie.posterURL)")
            description = movie.description
        }
    }
}
