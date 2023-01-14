//
//  Movie.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/13/23.
//

import Foundation

struct Movie: Decodable {
    let adult: Bool
    let backdropPath: String?
    let belongsToCollection: MovieCollection?
    let budget: Int64
    let genres: [Genre]
    let homepage: String?
    let id: Int64
    let originalTitle: String?
    let overview: String?
    let popularity: Double
    let posterPath: String?
    let releaseDate: String?
    let revenue: Int64
    let runtime: Int?
    let status: String
    let title: String?
    let voteAverage: Double
    let voteCount: Int64
}

struct MovieCollection: Decodable {
    let id: Int64?
    let name: String?
    let posterPath: String?
    let backdropPath: String?
}

struct Genre: Decodable {
    let id: Int
    let name: String
}
