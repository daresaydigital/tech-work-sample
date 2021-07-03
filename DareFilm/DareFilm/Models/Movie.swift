//
//  Movie.swift
//  DareFilm
//
//  Created by Johannes Loor on 2021-07-02.
//

import Foundation

struct MovieDataResponse: Codable {
    var results: [Movie]
}

struct Movie: Identifiable, Codable {
    var id: Int
    var title: String
    var overview: String
    var voteAverage: Double
    var posterPath: String
    var backdropPath: String?
    var releaseDate: String?
}
