//
//  Movie.swift
//  TheMovieDB
//
//  Created by Ali Sani on 12/11/21.
//

import Foundation

// MARK: - Movie
struct Movie: Decodable {
    let id: Int
    let title: String
    let voteAverage: Double
    let releaseDate: Date
    private let posterPath: String?
    
    var posterURL: URL? {
        guard let path = posterPath else { return nil }
        return URL(string: "https://image.tmdb.org/t/p/w500\(path)")
    }
}

// MARK: - MoviesResponse
struct MoviesResponse: Decodable {
    let totalPages : Int
    let results: [Movie]
}
