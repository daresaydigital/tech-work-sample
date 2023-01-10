//
//  TrendingResult.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import Foundation

struct TrendingResult: Decodable {
    let posterPath: String?
    let adult: Bool?
    let overview: String?
    let releaseDate: String?
    let genreId: [Int]?
    let id: Int64?
    let originalTitle: String?
    let originalLanguage: String?
    let title: String?
    let backdropPath: String?
    let popularity: Double?
    let voteCount: Int64?
    let video: Bool?
    let voteAverage: Double?
}
