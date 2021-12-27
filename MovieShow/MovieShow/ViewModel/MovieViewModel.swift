//
//  MovieViewModel.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-27.
//

import Foundation

struct MovieViewModel {
    let id: Int
    let title: String
    let ratingText: String
    let releaseYear: String
    let durationText: String
    let postureURL: URL
    let backdropURL: URL
    
    init(movie: Movie) {
        self.id = movie.id
        self.title = movie.title
        self.ratingText = movie.ratingText
        self.releaseYear = movie.releaseYear
        self.durationText = movie.durationText
        self.postureURL = movie.postureURL
        self.backdropURL = movie.backdropURL
    }
}
