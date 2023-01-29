//
//  Movie.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation

class Movie: Codable, Equatable {
    static func == (lhs: Movie, rhs: Movie) -> Bool {
        lhs.id == rhs.id
    }
    
    let adult: Bool
    let backdropPath: String
    let genreIDS: [Int]?
    let id: Int
    let originalLanguage: String
    let originalTitle: String
    let overview: String
    let popularity: Double
    let posterPath: String?
    let releaseDate: String
    let title: String
    let video: Bool
    let voteAverage: Double
    let voteCount: Int
    let imdbID: String?
    let tagline: String?
    var isFaved: Bool?
    
    enum CodingKeys: String, CodingKey {
        case adult
        case overview
        case popularity
        case id
        case title
        case video
        case backdropPath = "backdrop_path"
        case genreIDS = "genre_ids"
        case originalLanguage = "original_language"
        case originalTitle = "original_title"
        case posterPath = "poster_path"
        case releaseDate = "release_date"
        case voteAverage = "vote_average"
        case voteCount = "vote_count"
        case imdbID = "imdb_id"
        case tagline
        case isFaved
    }
    
    init(adult: Bool, backdropPath: String, genreIDS: [Int]?, id: Int,
         originalLanguage: String, originalTitle: String,
         overview: String, popularity: Double, posterPath: String?,
         releaseDate: String, title: String, video: Bool, voteAverage: Double,
         voteCount: Int, imdbID: String?, tagline: String?, isFaved: Bool? = nil) {
        self.adult = adult
        self.backdropPath = backdropPath
        self.genreIDS = genreIDS
        self.id = id
        self.originalLanguage = originalLanguage
        self.originalTitle = originalTitle
        self.overview = overview
        self.popularity = popularity
        self.posterPath = posterPath
        self.releaseDate = releaseDate
        self.title = title
        self.video = video
        self.voteAverage = voteAverage
        self.voteCount = voteCount
        self.imdbID = imdbID
        self.tagline = tagline
        self.isFaved = isFaved
    }
}
