//
//  Movies.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-28.
//

import Foundation

extension ServerModels {
    enum Movies {
        struct Request {}
        
        typealias Response = [MoviesModel]
    }
}

// MARK: - MovieModel
struct MoviesModel: ServerModel {
    let adult: Bool?
    let backdropPath: String?
    let genreIDS: [Int]?
    let movieID: Int?
    let originalLanguage, originalTitle, overview: String?
    let popularity: Double?
    let posterPath, releaseDate, title: String?
    let video: Bool?
    let voteAverage: Double?
    let voteCount: Int?
    var isFaved: Bool = false
    
    enum CodingKeys: String, CodingKey {
        case adult
        case backdropPath
        case genreIDS = "genre_ids"
        case movieID = "id"
        case originalLanguage
        case originalTitle
        case overview, popularity
        case posterPath
        case releaseDate
        case title, video
        case voteAverage
        case voteCount
    }
}
