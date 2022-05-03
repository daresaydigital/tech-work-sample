//
//  Movies.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/26/22.
//

import Foundation

struct Movies: Codable {
    let results: [Movie]
}

struct Movie: Codable {
    let title: String
    let poster: String?
    let id: Int
    let voteAverage: Double

    enum CodingKeys: String, CodingKey {
        case poster = "poster_path"
        case voteAverage = "vote_average"
        case title, id
    }
}

struct CoreDataMovie {
    let title: String
    let poster: Data
    let id: Int
    let date: Date
    let voteAverage: Double
}

struct MovieDetail: Codable {
    let title: String
    let poster: String?
    let id: Int
    let genres: [Genres]
    let overview: String?
    let voteAverage: Double
    let releaseDate: String
    let reviewsCount: Int

    enum CodingKeys: String, CodingKey {
        case poster = "poster_path"
        case releaseDate = "release_date"
        case reviewsCount = "vote_count"
        case voteAverage = "vote_average"
        case title, id, genres, overview
    }
}
