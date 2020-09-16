//
//  Movie.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import Foundation

struct Movie: Codable {
    let id: Int
    let title: String
    let overview: String
    let posterPath: String
    let backdropPath: String?
    let releaseDate: String
    let genreIDs: [Int]
    let numberOfTotalVotes: Int
    let rating: Double

    enum CodingKeys: String, CodingKey {
        case id = "id"
        case title = "original_title"
        case overview
        case posterPath = "poster_path"
        case backdropPath = "backdrop_path"
        case releaseDate = "release_date"
        case genreIDs = "genre_ids"
        case numberOfTotalVotes = "vote_count"
        case rating = "vote_average"
    }
}
