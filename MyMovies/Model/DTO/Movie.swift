//
//  Movie.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/13/23.
//

import Foundation

struct Movie: Decodable {
    let genres: [Genre]
    let originalTitle: String?
    let overview: String?
    let posterPath: String?
    let releaseDate: String?
    let title: String?
    let voteAverage: Double
    let voteCount: Int64
}

struct Genre: Decodable {
    let name: String
}
