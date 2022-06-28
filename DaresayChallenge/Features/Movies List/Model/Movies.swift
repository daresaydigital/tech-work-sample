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
        
        typealias Response = MovieSchema
    }
}

struct MovieSchema: ServerModel {
    let page: UInt?
    let results: [MoviesModel]?
    let totalPages, totalResults: UInt?
}

// MARK: - MovieModel
struct MoviesModel: ServerModel {
    let adult: Bool?
    let backdropPath: String?
    let genreIDS: [Int]?
    let movieID: Int?
    let originalLanguage, originalTitle, overview: String?
    let popularity: Double?
    let posterPath: String?
    let releaseDate: String?
    let title: String?
    let video: Bool?
    let voteAverage: Double?
    let voteCount: Int?
    var isFaved: Bool = false
    
    var imageURL: URL? {
        imageURL(posterPath)
    }
    
    enum CodingKeys: String, CodingKey {
        case adult
        case backdropPath
        case genreIDS = "genreIds"
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
    
    private func imageURL(_ url: String?) -> URL? {
        guard let url = url else { return nil }
        let urlBuilder = ImageBaseUrlBuilder(forTypeAndSize: .poster(.w342))
        let fullUrl = urlBuilder.createURL(filePath: url)
        return fullUrl
    }
}
