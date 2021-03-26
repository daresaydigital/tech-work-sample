//
//  MoviePayload.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-24.
//

import Foundation
import RxDataSources

struct MoviePayload: Codable {
    let page: Int
    let results: [Movie]
    let totalPages, totalResults: Int

    enum CodingKeys: String, CodingKey {
        case page, results
        case totalPages = "total_pages"
        case totalResults = "total_results"
    }
}

var mDateFormatter: DateFormatter = {
    let formatter = DateFormatter()
    formatter.dateFormat = "yyyy-MM-dd"
    //    formatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
    return formatter
}()

struct Movie: Codable {
    let adult: Bool
    let backdropPath: String
    let genreIDS: [Int]
    let id: Int
    let originalLanguage, originalTitle, overview: String
    let popularity: Double
    let posterPath, releaseDate, title: String
    let video: Bool
    let voteAverage: Double
    let voteCount: Int

    enum CodingKeys: String, CodingKey {
        case adult
        case backdropPath = "backdrop_path"
        case genreIDS = "genre_ids"
        case id
        case originalLanguage = "original_language"
        case originalTitle = "original_title"
        case overview, popularity
        case posterPath = "poster_path"
        case releaseDate = "release_date"
        case title, video
        case voteAverage = "vote_average"
        case voteCount = "vote_count"
    }
}

extension Movie: Equatable, Comparable {
    static func == (lhs: Movie, rhs: Movie) -> Bool {
        lhs.id == rhs.id
    }

    static func < (lhs: Movie, rhs: Movie) -> Bool {
        guard let lhsDate = mDateFormatter.date(from: lhs.releaseDate),
              let rhsDate = mDateFormatter.date(from: rhs.releaseDate) else { return false }
        return lhsDate < rhsDate
    }
}

extension Movie: IdentifiableType {
    var identity: Int { id }
}
